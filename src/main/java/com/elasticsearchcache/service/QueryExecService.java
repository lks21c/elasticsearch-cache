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
        StringBuilder sb = new StringBuilder();
        StringBuilder qb = new StringBuilder();

        logger.info("isMultiSearch = " + isMultiSearch);

        if (isMultiSearch) {
            for (QueryPlan qp : queryPlanList) {
                if (!StringUtils.isEmpty(qp.getPreQuery())) {
                    qb.append(qp.getPreQuery());
                }
                if (!StringUtils.isEmpty(qp.getQuery())) {
                    qb.append(qp.getQuery());
                }
                if (!StringUtils.isEmpty(qp.getPostQuery())) {
                    qb.append(qp.getPostQuery());
                }
            }
        } else {
            targetUrl = targetUrl.replace("_search", "_msearch");
            for (QueryPlan qp : queryPlanList) {
                if (!StringUtils.isEmpty(qp.getPreQuery())) {
                    qb.append("{}" + "\n");
                    qb.append(qp.getPreQuery().replace("\n","") + "\n");
                }
                if (!StringUtils.isEmpty(qp.getQuery())) {
                    qb.append("{}" + "\n");
                    qb.append(qp.getQuery().replace("\n","") + "\n");
                }
                if (!StringUtils.isEmpty(qp.getPostQuery())) {
                    qb.append("{}" + "\n");
                    qb.append(qp.getPostQuery().replace("\n","") + "\n");
                }
            }
        }

        List<Map<String, Object>> respes = null;
        if (!StringUtils.isEmpty(qb.toString())) {
            long beforeManipulateBulkQuery = System.currentTimeMillis();
            HttpResponse res = null;
            try {
                logger.info("executeQuery curl -X POST -H 'Content-Type: application/json' -L '" + targetUrl + "' " + " --data '" + qb.toString() + "'");
                res = esService.executeQuery(targetUrl, qb.toString());
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

            if(!isMultiSearch) {
                for(int i = 0; i < respes.size();i++) {
                    respes.get(i).remove("status");
                }
            }
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
                    try {
                        String postResBody = JsonUtil.convertAsString(respes.get(responseCnt++));
                        cacheService.putCache(postResBody, queryPlanList.get(i));
                        postDhbList = parsingService.getDhbList(postResBody);
                        mergedDhbList.addAll(postDhbList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }
                    logger.info("after convertAsString");

                    // put cache
                    logger.info("try post put cache");
                    logger.info("after post query executed");
                }
                String resBody = responseBuildService.generateRes(!isMultiSearch, mergedDhbList, queryPlanList.get(i).getAggsKey());

                logger.info("after generateRes");

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
