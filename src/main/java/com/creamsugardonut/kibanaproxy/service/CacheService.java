package com.creamsugardonut.kibanaproxy.service;

import com.creamsugardonut.kibanaproxy.conts.CacheMode;
import com.creamsugardonut.kibanaproxy.repository.CacheRepository;
import com.creamsugardonut.kibanaproxy.util.IndexNameUtil;
import com.creamsugardonut.kibanaproxy.util.JsonUtil;
import com.creamsugardonut.kibanaproxy.vo.CachePlan;
import com.creamsugardonut.kibanaproxy.vo.DateHistogramBucket;
import org.apache.commons.lang.SerializationUtils;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Parse 1 depth aggregation
        // Get aggs
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

        if (CacheMode.ALL.equals(plan.getCacheMode())) {
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
        } else {
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
            if (interval != null) {
                if ((interval.contains("d") && startDt.getSecondOfDay() == 0)
                        || (interval.contains("h") && startDt.getMinuteOfHour() == 0 && startDt.getSecondOfMinute() == 0)
                        || (interval.contains("m") && startDt.getSecondOfMinute() == 0)) {
                    logger.info("cacheable " + JsonUtil.convertAsString(aggs));
                    cacheRepository.putCache(body, indexName, JsonUtil.convertAsString(queryWithoutRange), JsonUtil.convertAsString(aggs), interval);
                }
            }
            return body;
        }
    }

    public CachePlan checkCachePlan(String interval, DateTime startDt, DateTime endDt) {
        CachePlan cachePlan = new CachePlan();
        if (interval != null) {
            if (interval.contains("d")) { //day
                if (startDt.getMillisOfDay() == 0) { //pre range doesn't exist
                    cachePlan.setStartDt(startDt);
                } else { //pre range exists
                    DateTime newStartDt = startDt.plusDays(1).withTimeAtStartOfDay();
                    DateTime preStartDt = startDt;
                    DateTime preEndDt = newStartDt.minusMillis(1);
                    cachePlan.setPreStartDt(preStartDt);
                    cachePlan.setPreEndDt(preEndDt);
                    cachePlan.setStartDt(newStartDt);
                }

                if (endDt.getMillisOfDay() == new DateTime().withTimeAtStartOfDay().minusMillis(1).getMillisOfDay()) { //end range doesn't exist
                    cachePlan.setEndDt(endDt);
                } else { //end range exists
                    DateTime postStartDt = endDt.withTimeAtStartOfDay();
                    DateTime postEndDt = endDt;
                    DateTime newEndDt = postStartDt.minusMillis(1);
                    cachePlan.setPostStartDt(postStartDt);
                    cachePlan.setPostEndDt(postEndDt);
                    cachePlan.setEndDt(newEndDt);
                }
            }
            if (interval.contains("h")) { //hour
                if (startDt.getMinuteOfHour() == 0
                        && startDt.getSecondOfMinute() == 0
                        && startDt.getMillisOfSecond() == 0) { //pre range doesn't exist
                    cachePlan.setStartDt(startDt);
                } else { //pre range exists
                    DateTime newStartDt = startDt.plusHours(1).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
                    DateTime preStartDt = startDt;
                    DateTime preEndDt = newStartDt.minusMillis(1);
                    cachePlan.setPreStartDt(preStartDt);
                    cachePlan.setPreEndDt(preEndDt);
                    cachePlan.setStartDt(newStartDt);
                }

                if (endDt.getMinuteOfHour() == 59
                        && endDt.getSecondOfMinute() == 59
                        && endDt.getMillisOfSecond() == new DateTime().withTimeAtStartOfDay().minusMillis(1).getMillisOfDay()) { //end range doesn't exist
                    cachePlan.setEndDt(endDt);
                } else { //end range exists
                    DateTime postStartDt = endDt.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
                    DateTime postEndDt = endDt;
                    DateTime newEndDt = postStartDt.minusMillis(1);
                    cachePlan.setPostStartDt(postStartDt);
                    cachePlan.setPostEndDt(postEndDt);
                    cachePlan.setEndDt(newEndDt);
                }
            } else if (interval.contains("m")) { //minute
                if (startDt.getSecondOfMinute() == 0
                        && startDt.getMillisOfSecond() == 0) { //pre range doesn't exist
                    cachePlan.setStartDt(startDt);
                } else { //pre range exists
                    DateTime newStartDt = startDt.plusMinutes(1).withSecondOfMinute(0).withMillisOfSecond(0);
                    DateTime preStartDt = startDt;
                    DateTime preEndDt = newStartDt.minusMillis(1);
                    cachePlan.setPreStartDt(preStartDt);
                    cachePlan.setPreEndDt(preEndDt);
                    cachePlan.setStartDt(newStartDt);
                }

                if (endDt.getSecondOfMinute() == 59
                        && endDt.getMillisOfSecond() == new DateTime().withTimeAtStartOfDay().minusMillis(1).getMillisOfSecond()) { //end range doesn't exist
                    cachePlan.setEndDt(endDt);
                } else { //end range exists
                    DateTime postStartDt = endDt.withSecondOfMinute(0).withMillisOfSecond(0);
                    DateTime postEndDt = endDt;
                    DateTime newEndDt = postStartDt.minusMillis(1);
                    cachePlan.setPostStartDt(postStartDt);
                    cachePlan.setPostEndDt(postEndDt);
                    cachePlan.setEndDt(newEndDt);
                }
            }
        }
        return cachePlan;
    }

    private CachePlan checkCacheMode(String interval, CachePlan plan, List<DateHistogramBucket> dhbList) {
        if (interval != null) {
            int intervalNum = -1;
            if (interval.contains("d")) {
                intervalNum = Integer.parseInt(interval.replace("d", ""));
            } else if (interval.contains("m")) {
                intervalNum = Integer.parseInt(interval.replace("m", ""));
            }

            if ("1d".equals(interval)) {
                if (Days.daysBetween(plan.getStartDt(), plan.getEndDt()).getDays() + 1 == dhbList.size()
                        && plan.getPreStartDt() == null
                        && plan.getPreEndDt() == null
                        && plan.getPostStartDt() == null
                        && plan.getPostEndDt() == null) {
                    plan.setCacheMode(CacheMode.ALL);
                } else if (dhbList.size() > 0) {
                    plan.setCacheMode(CacheMode.PARTIAL);
                }
            } else if ("1m".equals(interval)) {
                if (dhbList.size() > 0) {
                    if (Minutes.minutesBetween(plan.getStartDt(), plan.getEndDt()).getMinutes() == dhbList.size()
                            && plan.getPreStartDt() == null
                            && plan.getPreEndDt() == null
                            && plan.getPostStartDt() == null
                            && plan.getPostEndDt() == null) {
                        plan.setCacheMode(CacheMode.ALL);
                    } else if (dhbList.size() > 0) {
                        plan.setCacheMode(CacheMode.PARTIAL);
                    }
                }
            }
        }
        plan.setCacheMode(CacheMode.NOCACHE);
        return plan;
    }
}
