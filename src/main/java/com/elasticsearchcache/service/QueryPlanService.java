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
public class QueryPlanService {
    private static Logger logger = LoggerFactory.getLogger(QueryPlanService.class);

    @Autowired
    ElasticSearchService esService;

    @Autowired
    ParsingService parsingService;

    @Autowired
    CacheService cacheService;

    public String executeQuery(String targetUrl, List<QueryPlan> queryPlanList) {
        StringBuilder sb = new StringBuilder();
        StringBuilder qb = new StringBuilder();
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

        long beforeManipulateBulkQuery = System.currentTimeMillis();
        HttpResponse res = null;
        try {
            res = esService.executeQuery(targetUrl, qb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MethodNotSupportedException e) {
            e.printStackTrace();
        }

        if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            logger.info("it's not ok code.");
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
//        logger.info("refactor res = " + bulkRes);

        Map<String, Object> resMap = parsingService.parseXContent(bulkRes);
        List<Map<String, Object>> respes = (List<Map<String, Object>>) resMap.get("responses");

        StringBuilder mergedRes = new StringBuilder();
        mergedRes.append("{");
        mergedRes.append("\"responses\":[");
        int responseCnt = 0;
        for (int i = 0; i < queryPlanList.size(); i++) {
            logger.info("query plan cache mode(" + i + ") = " + queryPlanList.get(i).getCachePlan().getCacheMode());
            if (CacheMode.ALL.equals(queryPlanList.get(i).getCachePlan().getCacheMode())) {
                String resBody = cacheService.generateRes(queryPlanList.get(i).getDhbList());
                if (i != 0) {
                    mergedRes.append(",");
                }
                if ("terms".equals(queryPlanList.get(i).getAggsType())) {
                    resBody = cacheService.generateTermsRes(resBody);
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
                    preDhbList = cacheService.getDhbList(preResBody);
                    mergedDhbList.addAll(preDhbList);
                }

                logger.info("partial cache size = " + queryPlanList.get(i).getDhbList().size());
                mergedDhbList.addAll(queryPlanList.get(i).getDhbList());

                List<DateHistogramBucket> postDhbList = null;
                if (!StringUtils.isEmpty(queryPlanList.get(i).getPostQuery())) {
                    logger.info("post query executed");
                    String postResBody = JsonUtil.convertAsString(respes.get(responseCnt++));
                    // put cache
                    logger.info("try post put cache");
                    cacheService.putCache(postResBody, queryPlanList.get(i));
                    postDhbList = cacheService.getDhbList(postResBody);
                    mergedDhbList.addAll(postDhbList);
                }
                String resBody = cacheService.generateRes(mergedDhbList);

                if (i != 0) {
                    mergedRes.append(",");
                }

                if ("terms".equals(queryPlanList.get(i).getAggsType())) {
                    resBody = cacheService.generateTermsRes(resBody);
                }
                mergedRes.append(resBody);
            } else {
                if (!StringUtils.isEmpty(queryPlanList.get(i).getQuery())) {
                    if (i != 0) {
                        mergedRes.append(",");
                    }
                    String resBody = JsonUtil.convertAsString(respes.get(responseCnt++));
                    // put cache
                    cacheService.putCache(resBody, queryPlanList.get(i));
                    if ("terms".equals(queryPlanList.get(i).getAggsType())) {
                        logger.info("terms nocache");
                        resBody = cacheService.generateTermsRes(resBody);
                    }
                    mergedRes.append(resBody);
                }
            }
        }
        mergedRes.append("]");
        mergedRes.append("}");


//        logger.info("merged res = " + mergedRes.toString());

//                        res = esService.executeQuery(targetUrl, reqBody);
        sb.append(mergedRes.toString());
        return sb.toString();
    }
}
