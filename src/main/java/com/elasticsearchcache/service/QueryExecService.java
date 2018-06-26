package com.elasticsearchcache.service;

import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.DateHistogramBucket;
import com.elasticsearchcache.vo.QueryPlan;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lks21c
 */
@Service
public class QueryExecService {
    private static Logger logger = LoggerFactory.getLogger(QueryExecService.class);

    @Autowired
    ElasticSearchService esService;

    @Autowired
    ParsingService parsingService;

    @Autowired
    CacheService cacheService;

    @Autowired
    ResponseBuildService responseBuildService;

    public String executeQuery(boolean isMultiSearch, String targetUrl, List<QueryPlan> queryPlanList) {
        logger.info("isMultiSearch = " + isMultiSearch + " " + queryPlanList.size());

        if (queryPlanList.size() == 1 && !isMultiSearch) {
            if (CacheMode.NOCACHE.equals(queryPlanList.get(0).getCachePlan().getCacheMode())) {
                HttpResponse res = null;
                try {
                    res = esService.executeQuery(targetUrl, queryPlanList.get(0).getQuery());
                    if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        logger.error("it's not ok code.");
                        return null;
                    }
                    return EntityUtils.toString(res.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (MethodNotSupportedException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder qb = new StringBuilder();

        int queryCnt = 0;
        if (isMultiSearch) {
            for (QueryPlan qp : queryPlanList) {
                if (!StringUtils.isEmpty(qp.getPreQuery())) {
                    queryCnt++;
                    qb.append(qp.getPreQuery().replace("\n", ""));
                }
                if (!StringUtils.isEmpty(qp.getQuery())) {
                    queryCnt++;
                    qb.append(qp.getQuery().replace("\n", ""));
                }
                if (!StringUtils.isEmpty(qp.getPostQuery())) {
                    queryCnt++;
                    qb.append(qp.getPostQuery().replace("\n", ""));
                }
            }
        } else {
            for (QueryPlan qp : queryPlanList) {
                if (!StringUtils.isEmpty(qp.getPreQuery())) {
                    queryCnt++;
                    qb.append("{}" + "\n");
                    qb.append(qp.getPreQuery() + "\n");
                }
                if (!StringUtils.isEmpty(qp.getQuery())) {
                    queryCnt++;
                    qb.append("{}" + "\n");
                    qb.append(qp.getQuery() + "\n");
                }
                if (!StringUtils.isEmpty(qp.getPostQuery())) {
                    queryCnt++;
                    qb.append("{}" + "\n");
                    qb.append(qp.getPostQuery() + "\n");
                }
            }
        }

        logger.info("queryCnt = " + queryCnt);

        List<Map<String, Object>> respes = null;
        if (!StringUtils.isEmpty(qb.toString())) {
            long beforeManipulateBulkQuery = System.currentTimeMillis();
            HttpResponse res = null;
            try {
                if (queryCnt > 1) {
                    targetUrl = targetUrl.replace("_search", "_msearch");
                    logger.info("executeQuery curl -X POST -H 'Content-Type: application/json' -L '" + targetUrl + "' " + " --data '" + qb.toString() + "'");
                    res = esService.executeQuery(targetUrl, qb.toString());
                } else {
                    logger.info("executeQuery curl -X POST -H 'Content-Type: application/json' -L '" + targetUrl + "' " + " --data '" + qb.toString() + "'");
                    res = esService.executeQuery(targetUrl, qb.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MethodNotSupportedException e) {
                e.printStackTrace();
            }

            if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.error("it's not ok code.");

                return null;
            }

            long afterManipulateBulkQuery = System.currentTimeMillis() - beforeManipulateBulkQuery;
            logger.info("afterManipulateBulkQuery = " + afterManipulateBulkQuery);
            String bulkRes = null;
            try {
                bulkRes = EntityUtils.toString(res.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }

            logger.info("bulkRes res = " + bulkRes);

            respes = parsingService.parseResponses(bulkRes);
        }

        StringBuilder mergedRes = new StringBuilder();

        if (isMultiSearch) {
            mergedRes.append("{");
            mergedRes.append("\"responses\":[");
        }
        int responseCnt = 0;
        for (int i = 0; i < queryPlanList.size(); i++) {
            String log = "query plan cache mode(" + i + ") = " + queryPlanList.get(i).getCachePlan().getCacheMode();
            if (CacheMode.PARTIAL.equals(queryPlanList.get(i).getCachePlan().getCacheMode())) {
                log += "(" + queryPlanList.get(i).getDhbList().size() + ")";
            }
            logger.info(log);

            if (CacheMode.ALL.equals(queryPlanList.get(i).getCachePlan().getCacheMode())) {
                String resBody = responseBuildService.generateRes(!isMultiSearch, queryPlanList.get(i).getDhbList(), queryPlanList.get(i).getAggsKey());
                if (i != 0) {
                    mergedRes.append(",");
                }
                if ("terms".equals(queryPlanList.get(i).getAggsType())) {
                    resBody = parsingService.generateTermsRes(resBody);
                }
                mergedRes.append(resBody);
            } else if (CacheMode.PARTIAL.equals(queryPlanList.get(i).getCachePlan().getCacheMode())) {
                List<DateHistogramBucket> mergedDhbList = new ArrayList<>();
                List<DateHistogramBucket> preDhbList = null;
                if (!StringUtils.isEmpty(queryPlanList.get(i).getPreQuery())) {
                    logger.info("pre query executed");
                    String preResBody = JsonUtil.convertAsString(respes.get(responseCnt++));
                    // put cache
                    cacheService.putCache(preResBody, queryPlanList.get(i));
                    preDhbList = parsingService.getDhbList(preResBody);
                    mergedDhbList.addAll(preDhbList);
                }

//                logger.info("partial cache size = " + queryPlanList.get(i).getDhbList().size());
//                logger.info("partial cache = " + JsonUtil.convertAsString(queryPlanList.get(i).getDhbList()));
                mergedDhbList.addAll(queryPlanList.get(i).getDhbList());

                List<DateHistogramBucket> postDhbList = null;
                if (!StringUtils.isEmpty(queryPlanList.get(i).getPostQuery())) {
                    logger.info("post query executed");
                    logger.info(queryPlanList.get(i).getPostQuery());
                    String postResBody = JsonUtil.convertAsString(respes.get(responseCnt++));
                    // put cache
                    logger.debug("try post put cache");
                    cacheService.putCache(postResBody, queryPlanList.get(i));
                    postDhbList = parsingService.getDhbList(postResBody);
                    mergedDhbList.addAll(postDhbList);
                }
                String resBody = responseBuildService.generateRes(!isMultiSearch, mergedDhbList, queryPlanList.get(i).getAggsKey());

                if (i != 0) {
                    mergedRes.append(",");
                }

                if ("terms".equals(queryPlanList.get(i).getAggsType())) {
                    resBody = parsingService.generateTermsRes(resBody);
                }
                mergedRes.append(resBody);
            } else {
                if (!StringUtils.isEmpty(queryPlanList.get(i).getQuery())) {
                    if (i != 0) {
                        mergedRes.append(",");
                    }
                    String resBody = JsonUtil.convertAsString(respes.get(responseCnt++));
                    // put cache
                    logger.info("nocache put cache");
                    cacheService.putCache(resBody, queryPlanList.get(i));
                    if ("terms".equals(queryPlanList.get(i).getAggsType())) {
                        logger.info("terms nocache");
                        resBody = parsingService.generateTermsRes(resBody);
                    }
                    mergedRes.append(resBody);
                }
            }
        }
        if (isMultiSearch) {
            mergedRes.append("]");
            mergedRes.append("}");
        }

        logger.debug("merged res = " + mergedRes.toString());

//                        res = esService.executeQuery(targetUrl, reqBody);
        sb.append(mergedRes.toString());
        return sb.toString();
    }
}
