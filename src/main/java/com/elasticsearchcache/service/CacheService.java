package com.elasticsearchcache.service;

import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.repository.CacheRepository;
import com.elasticsearchcache.util.IndexNameUtil;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.util.PeriodUtil;
import com.elasticsearchcache.vo.CachePlan;
import com.elasticsearchcache.vo.DateHistogramBucket;
import org.apache.commons.lang.SerializationUtils;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lks21c
 */
@Service
public class CacheService {
    private static final Logger logger = LogManager.getLogger(CacheService.class);

    @Autowired
    private ElasticSearchService esService;

    @Autowired
    private ParsingService parsingService;

    @Autowired
    @Qualifier("EsCacheRepositoryImpl")
    private CacheRepository cacheRepository;

    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    public String manipulateQuery(String info) throws IOException, MethodNotSupportedException {
        logger.info("info = " + info);

        String[] arr = info.split("\n");
        Map<String, Object> iMap = parsingService.parseXContent(arr[0]);
        Map<String, Object> qMap = parsingService.parseXContent(arr[1]);

        List<String> idl = (List<String>) iMap.get("index");
        String indexName = IndexNameUtil.getIndexName(idl);

        for (String key : qMap.keySet()) {
            logger.info("key = " + key);
        }

        // Get gte, lte
        DateTime startDt = null, endDt = null;
        Map<String, Object> query = (Map<String, Object>) qMap.get("query");

        Map<String, Object> queryWithoutRange = (Map<String, Object>) SerializationUtils.clone((HashMap<String, Object>) query);
        Map<String, Object> bool = (Map<String, Object>) query.get("bool");
        List<Map<String, Object>> must = (List<Map<String, Object>>) bool.get("must");

        for (Map<String, Object> obj : must) {
            Map<String, Object> range = (Map<String, Object>) obj.get("range");
            if (range != null) {
                for (String rangeKey : range.keySet()) {
                    Long gte = (Long) ((Map<String, Object>) range.get(rangeKey)).get("gte");
                    Long lte = (Long) ((Map<String, Object>) range.get(rangeKey)).get("lte");
                    startDt = new DateTime(gte);
                    endDt = new DateTime(lte);

                    logger.info("startDt = " + startDt);
                    logger.info("endDt = " + endDt);
                }
            }
        }

        Map<String, Object> clonedBool = (Map<String, Object>) queryWithoutRange.get("bool");
        List<Map<String, Object>> clonedMust = (List<Map<String, Object>>) clonedBool.get("must");
        for (Map<String, Object> obj : clonedMust) {
            obj.remove("range");
        }
        logger.info("queryWithoutRange = " + queryWithoutRange);
        logger.info("query = " + query);

        logger.info("invoked here");

        // Get aggs
        Map<String, Object> aggs = (Map<String, Object>) qMap.get("aggs");

        // Parse Interval
        String interval = parseInterval(aggs);

        CachePlan plan = checkCachePlan(interval, startDt, endDt);
        logger.info("cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("cachePlan getStartDt = " + plan.getStartDt());
        logger.info("cachePlan getEndDt = " + plan.getEndDt());
        logger.info("cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("cachePlan getPostEndDt = " + plan.getPostEndDt());

        List<DateHistogramBucket> dhbList = cacheRepository.getCache(indexName, JsonUtil.convertAsString(queryWithoutRange), JsonUtil.convertAsString(aggs), plan.getStartDt(), plan.getEndDt());
        logger.info("dhbList = " + JsonUtil.convertAsString(dhbList));

        plan = checkCacheMode(interval, plan, dhbList);
        logger.info("cacheMode = " + plan.getCacheMode() + " cache size : " + dhbList.size());
        logger.info("after cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("after cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("after cachePlan getStartDt = " + plan.getStartDt());
        logger.info("after cachePlan getEndDt = " + plan.getEndDt());
        logger.info("after cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("after cachePlan getPostEndDt = " + plan.getPostEndDt());

        if (CacheMode.ALL.equals(plan.getCacheMode())) {

            //TODO: manipulates took and so on.
            String res = "{\n" +
                    "  \"responses\": [\n" +
                    "    {\n" +
                    "      \"took\": 3,\n" +
                    "      \"timed_out\": false,\n" +
                    "      \"_shards\": {\n" +
                    "        \"total\": 55,\n" +
                    "        \"successful\": 55,\n" +
                    "        \"failed\": 0\n" +
                    "      },\n" +
                    "      \"hits\": {\n" +
                    "        \"total\": 2718971,\n" +
                    "        \"max_score\": 0,\n" +
                    "        \"hits\": []\n" +
                    "      },\n" +
                    "      \"aggregations\": {\n" +
                    "        \"2\": {\n" +
                    "          \"buckets\": \n";

            List<Map<String, Object>> buckets = new ArrayList<>();
            for (DateHistogramBucket bucket : dhbList) {
                buckets.add(bucket.getBucket());
            }
            res += JsonUtil.convertAsString(buckets);

            res += "            \n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"status\": 200\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            logger.info("final res = " + res);
            return res;
        }

        // partial 처리
        // 이전 쿼리가 존재하면 호출
        // 현재 캐시 호출
        // 이후 쿼리 존재하면 호출
        // responses merge, 같은 key끼리 데이터 sum

        else {
            logger.info("else, so original request invoked " + startDt.getSecondOfDay());

            HttpResponse res = esService.executeQuery(esUrl + "/_msearch", info);

            String body = null;
            try {
                body = EntityUtils.toString(res.getEntity());
            } catch (Exception e) {
                logger.info("exception occurred");
                e.printStackTrace();
            }

//            body = body.replace("\"timed_out\":false,", "");
            logger.info("original body = " + body);

            // Cacheable
            if (checkCacheable(interval, startDt)) {
                logger.info("cacheable " + JsonUtil.convertAsString(aggs));
                cacheRepository.putCache(body, indexName, JsonUtil.convertAsString(queryWithoutRange), JsonUtil.convertAsString(aggs), interval);
            }
            return body;
        }

    }

    private String parseInterval(Map<String, Object> aggs) {
        String interval = null;
        if (aggs.size() == 1) {
            for (String aggsKey : aggs.keySet()) {
                Map<String, Object> firstDepthAggs = (Map<String, Object>) aggs.get(aggsKey);

                Map<String, Object> date_histogram = (Map<String, Object>) firstDepthAggs.get("date_histogram");

                if (date_histogram != null) {
                    interval = (String) date_histogram.get("interval");
                    logger.info("interval = " + interval);
                }
            }
        }
        return interval;
    }

    private boolean checkCacheable(String interval, DateTime startDt) {
        if (interval != null) {
            if (PeriodUtil.getRestMills(startDt, getPeriodUnit(interval, parseIntervalNum(interval))) == 0) {
                return true;
            }
        }
        return false;
    }

    public CachePlan checkCachePlan(String interval, DateTime startDt, DateTime endDt) {
        CachePlan cachePlan = new CachePlan();
        if (interval != null) {
            int periodUnit = getPeriodUnit(interval, parseIntervalNum(interval));
            logger.info("periodUnit = " + periodUnit);
            if (PeriodUtil.getRestMills(startDt, periodUnit) == 0) { //pre range doesn't exist
                cachePlan.setStartDt(startDt);
            } else { //pre range exists
                DateTime newStartDt = PeriodUtil.getNewStartDt(startDt, periodUnit);
                DateTime preStartDt = startDt;
                DateTime preEndDt = newStartDt.minusMillis(1);
                cachePlan.setPreStartDt(preStartDt);
                cachePlan.setPreEndDt(preEndDt);
                cachePlan.setStartDt(newStartDt);
            }

            if (PeriodUtil.getRestMills(endDt, periodUnit) == periodUnit - 1) { //end range doesn't exist
                cachePlan.setEndDt(endDt);
            } else { //end range exists
                DateTime postEndDt = endDt;
                DateTime postStartDt = endDt.minus(PeriodUtil.getRestMills(endDt, periodUnit));
                DateTime newEndDt = postStartDt.minusMillis(1);
                cachePlan.setPostStartDt(postStartDt);
                cachePlan.setPostEndDt(postEndDt);
                cachePlan.setEndDt(newEndDt);
            }
        }
        return cachePlan;
    }

    private CachePlan checkCacheMode(String interval, CachePlan plan, List<DateHistogramBucket> dhbList) {
        if (interval != null) {
            int intervalNum = parseIntervalNum(interval);
            int periodUnit = getPeriodUnit(interval, intervalNum);

            if (periodUnit == -1) {
                plan.setCacheMode(CacheMode.NOCACHE);
                return plan;
            }

            int periodBetween = PeriodUtil.periodBetween(plan.getStartDt(), plan.getEndDt(), (intervalNum * periodUnit));
            logger.info("periodBetween = " + periodBetween);

            if (periodBetween + 1 == dhbList.size()
                    && plan.getPreStartDt() == null
                    && plan.getPreEndDt() == null
                    && plan.getPostStartDt() == null
                    && plan.getPostEndDt() == null) {
                plan.setCacheMode(CacheMode.ALL);
                return plan;
            } else if (dhbList.size() > 0) {
                DateTime preDateTime = null;
                boolean isSuccessive = false;
                for (DateHistogramBucket dhb : dhbList) {
                    if (preDateTime != null && PeriodUtil.periodBetween(preDateTime, dhb.getDate(), periodUnit) == 1) {
                        isSuccessive = true;
                    } else {
                        isSuccessive = false;
                    }
                    preDateTime = dhb.getDate();
                }

                logger.info("isSuccessive = " + isSuccessive);
                if (isSuccessive) {
                    if (PeriodUtil.periodBetween(dhbList.get(0).getDate(), plan.getStartDt(), periodUnit) != 0) {
                        plan.setPreStartDt(plan.getStartDt());
                        plan.setStartDt(dhbList.get(0).getDate());
                        plan.setPreEndDt(dhbList.get(0).getDate().minusMillis(1));
                    }

                    if (PeriodUtil.periodBetween(dhbList.get(dhbList.size() - 1).getDate(), plan.getEndDt(), periodUnit) != 0) {
                        plan.setPostStartDt(dhbList.get(dhbList.size() - 1).getDate().plus(periodUnit));
                        plan.setPostEndDt(plan.getEndDt());
                        plan.setEndDt(dhbList.get(dhbList.size() - 1).getDate().plus(periodUnit).minusMillis(1));
                    }
                    plan.setCacheMode(CacheMode.PARTIAL);
                    return plan;
                }
            }
        }
        plan.setCacheMode(CacheMode.NOCACHE);
        return plan;
    }

    private int getPeriodUnit(String interval, int intervalNum) {
        int periodUnit = -1;
        if (interval.contains("d")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_DAY;
        } else if (interval.contains("h")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_HOUR;
        } else if (interval.contains("m")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_MINUTE;
        }
        return periodUnit;
    }

    private int parseIntervalNum(String interval) {
        int intervalNum = -1;
        if (interval.contains("d")) {
            intervalNum = Integer.parseInt(interval.replace("d", ""));
        } else if (interval.contains("h")) {
            intervalNum = Integer.parseInt(interval.replace("h", ""));
        } else if (interval.contains("m")) {
            intervalNum = Integer.parseInt(interval.replace("m", ""));
        }
        return intervalNum;
    }
}
