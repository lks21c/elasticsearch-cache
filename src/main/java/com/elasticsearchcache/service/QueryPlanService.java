package com.elasticsearchcache.service;

import com.elasticsearchcache.PreFilter;
import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.repository.CacheRepository;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.DateHistogramBucket;
import com.elasticsearchcache.vo.QueryPlan;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    private CachePlanService cachePlanService;

    @Autowired
    @Qualifier("EsCacheRepositoryImpl")
    private CacheRepository cacheRepository;

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
        long afterManipulateBulkQuery = System.currentTimeMillis() - beforeManipulateBulkQuery;
        logger.info("afterManipulateBulkQuery = " + afterManipulateBulkQuery);
        String bulkRes = null;
        try {
            bulkRes = EntityUtils.toString(res.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("refactor res = " + bulkRes);

        Map<String, Object> resMap = parsingService.parseXContent(bulkRes);
        try {
            logger.info("resMap = " + JsonUtil.convertAsString(resMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> respes = (List<Map<String, Object>>) resMap.get("responses");

        try {
            logger.info("respes = " + JsonUtil.convertAsString(respes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        StringBuilder mergedRes = new StringBuilder();
        mergedRes.append("{");
        mergedRes.append("\"responses\":[");
        int responseCnt = 0;
        for (int i = 0; i < queryPlanList.size(); i++) {
            logger.info("query plan cache mode = " + queryPlanList.get(i).getCachePlan().getCacheMode());
            if (CacheMode.ALL.equals(queryPlanList.get(i).getCachePlan().getCacheMode())) {
                String resBody = cacheService.generateRes(queryPlanList.get(i).getDhbList());
                if (i != 0) {
                    mergedRes.append(",");
                }
                mergedRes.append(resBody);

                //// Cacheable
                if (queryPlanList.get(i).getInterval() != null) {
                    List<DateHistogramBucket> originalDhbList = cacheService.getDhbList(resBody);
                    List<DateHistogramBucket> cacheDhbList = new ArrayList<>();
                    for (DateHistogramBucket dhb : originalDhbList) {
                        if (cachePlanService.checkCacheable(queryPlanList.get(i).getInterval(), dhb.getDate(), queryPlanList.get(i).getCachePlan().getStartDt(), queryPlanList.get(i).getCachePlan().getEndDt())) {
                            logger.info("cacheable");
                            cacheDhbList.add(dhb);
                        }
                    }
                    try {
                        cacheRepository.putCache(queryPlanList.get(i).getIndexName(), queryPlanList.get(i).getQueryWithoutRange(), queryPlanList.get(i).getAggs(), cacheDhbList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (CacheMode.PARTIAL.equals(queryPlanList.get(i).getCachePlan().getCacheMode())) {
                List<DateHistogramBucket> mergedDhbList = new ArrayList<>();
                List<DateHistogramBucket> preDhbList = null;
                if (!StringUtils.isEmpty(queryPlanList.get(i).getPreQuery())) {
                    try {
                        preDhbList = cacheService.getDhbList(JsonUtil.convertAsString(respes.get(responseCnt++)));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    mergedDhbList.addAll(preDhbList);
                }

                mergedDhbList.addAll(queryPlanList.get(i).getDhbList());

                List<DateHistogramBucket> postDhbList = null;
                if (!StringUtils.isEmpty(queryPlanList.get(i).getPostQuery())) {
                    try {
                        postDhbList = cacheService.getDhbList(JsonUtil.convertAsString(respes.get(responseCnt++)));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    mergedDhbList.addAll(postDhbList);
                }
                String resBody = cacheService.generateRes(mergedDhbList);

                if (i != 0) {
                    mergedRes.append(",");
                }
                mergedRes.append(resBody);
            } else {
                if (!StringUtils.isEmpty(queryPlanList.get(i).getQuery())) {

                    if (i != 0) {
                        mergedRes.append(",");
                    }
                    mergedRes.append(respes.get(responseCnt++));
                }
            }
        }
        mergedRes.append("]");
        mergedRes.append("}");


        logger.info("merged res = " + mergedRes.toString());

//                        res = esService.executeQuery(targetUrl, reqBody);
        sb.append(mergedRes.toString());
        return sb.toString();
    }
}
