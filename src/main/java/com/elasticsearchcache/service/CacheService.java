package com.elasticsearchcache.service;

import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.profile.ProfileService;
import com.elasticsearchcache.repository.CacheRepository;
import com.elasticsearchcache.util.IndexNameUtil;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.util.PeriodUtil;
import com.elasticsearchcache.vo.CachePlan;
import com.elasticsearchcache.vo.DateHistogramBucket;
import com.elasticsearchcache.vo.QueryPlan;
import org.apache.commons.lang.SerializationUtils;
import org.apache.http.MethodNotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
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
    private ParsingService parsingService;

    @Autowired
    private CachePlanService cachePlanService;

    @Autowired
    @Qualifier("EsCacheRepositoryImpl")
    private CacheRepository cacheRepository;

    @Autowired
    private ProfileService profileService;

    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    @Value("${esc.cache.terms}")
    private boolean enableTermsCache;

    @Value("${filedname.time}")
    private String timeFiledName;

    public QueryPlan manipulateQuery(String info) throws IOException, MethodNotSupportedException {
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
//        logger.info("query = " + query);

        logger.info("invoked here");

        // Get aggs
        Map<String, Object> aggs = (Map<String, Object>) qMap.get("aggs");

        // Parse Interval
        Map<String, Object> rtnMap = parseIntervalAndAggsType(aggs, getIntervalTerms(indexName, startDt, endDt));
        String interval = (String) rtnMap.get("interval");
        String aggsType = (String) rtnMap.get("aggsType");

        profileService.putQueryProfile(indexName, interval, iMap, qMap);

        // handle terms
        if (enableTermsCache && "terms".equals(aggsType)) {
            aggs = appendDateHistogram(aggs, getIntervalTerms(indexName, startDt, endDt));
            qMap.put("aggs", aggs);
        }

        CachePlan plan = cachePlanService.checkCachePlan(interval, startDt, endDt);
        logger.info("cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("cachePlan getStartDt = " + plan.getStartDt());
        logger.info("cachePlan getEndDt = " + plan.getEndDt());
        logger.info("cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("cachePlan getPostEndDt = " + plan.getPostEndDt());

        QueryPlan queryPlan = new QueryPlan();
        queryPlan.setCachePlan((CachePlan) SerializationUtils.clone(plan));
        queryPlan.setInterval(interval);
        queryPlan.setIndexName(indexName);
        queryPlan.setQueryWithoutRange(JsonUtil.convertAsString(queryWithoutRange));
        queryPlan.setAggs(JsonUtil.convertAsString(aggs));
        queryPlan.setAggsType(aggsType);

        DateTime beforeCacheMills = new DateTime();
        List<DateHistogramBucket> dhbList = cacheRepository.getCache(indexName, JsonUtil.convertAsString(queryWithoutRange), JsonUtil.convertAsString(aggs), plan.getStartDt(), plan.getEndDt());
        long afterCacheMills = new DateTime().getMillis() - beforeCacheMills.getMillis();

//        logger.info("dhbList = " + JsonUtil.convertAsString(dhbList));
        logger.info("afterCacheMills = " + afterCacheMills);

        plan = cachePlanService.checkCacheMode(interval, plan, dhbList);
        queryPlan.getCachePlan().setCacheMode(plan.getCacheMode());
        logger.info("cacheMode = " + plan.getCacheMode() + " cache size : " + dhbList.size());
        logger.info("after cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.info("after cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.info("after cachePlan getStartDt = " + plan.getStartDt());
        logger.info("after cachePlan getEndDt = " + plan.getEndDt());
        logger.info("after cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.info("after cachePlan getPostEndDt = " + plan.getPostEndDt());

        if (CacheMode.ALL.equals(plan.getCacheMode())) {
            queryPlan.setDhbList(dhbList);
            return queryPlan;
        } else if (CacheMode.PARTIAL.equals(plan.getCacheMode())) {
            DateTime measureDt = new DateTime();
            // execute pre query
            if (plan.getPreStartDt() != null && plan.getPreEndDt() != null) {
                Map<String, Object> preQmap = getManipulateQuery(qMap, plan.getPreStartDt(), plan.getPreEndDt());
                queryPlan.setPreQuery(JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(preQmap) + "\n");
            }

            long afterPreQueryMills = new DateTime().getMillis() - measureDt.getMillis();
            logger.info("after pre query = " + afterPreQueryMills);

            //dhbList
            queryPlan.setDhbList(dhbList);

            // execute post query
            if (plan.getPostStartDt() != null && plan.getPostEndDt() != null) {
                Map<String, Object> postQmap = getManipulateQuery(qMap, plan.getPostStartDt(), plan.getPostEndDt());
                queryPlan.setPostQuery(JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(postQmap) + "\n");
            }

            long afterPostQueryMills = new DateTime().getMillis() - measureDt.getMillis();
            logger.info("after post query = " + afterPostQueryMills);

            return queryPlan;
        } else {
            logger.info("else, so original request invoked " + startDt.getSecondOfDay());
            if ("terms".equals(aggsType)) {
                queryPlan.setQuery(JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(qMap) + "\n");
            } else {
                queryPlan.setQuery(info);
            }
            return queryPlan;
        }
    }

    private Map<String, Object> appendDateHistogram(Map<String, Object> aggs, String interval) {
        HashMap<String, Object> hashMap = new HashMap<>(aggs);
        HashMap<String, Object> newAggs = new HashMap<>();
        Map<String, Object> clonedAggs = (Map<String, Object>) SerializationUtils.clone(hashMap);

        HashMap<String, Object> dtEntry = new HashMap<>();
        dtEntry.put("field", timeFiledName);
        dtEntry.put("interval", interval);
        dtEntry.put("time_zone", PeriodUtil.getTimeZone());

        HashMap<String, Object> dtMap = new HashMap<>();
        dtMap.put("date_histogram", dtEntry);
        dtMap.put("aggs", clonedAggs);

        newAggs.put("dates", dtMap);
        return newAggs;
    }

    public String generateRes(List<DateHistogramBucket> dhbList) {
        //TODO: manipulates took and so on.
        String res = "" +
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
                "    }\n";

        return res;
    }

    private Map<String, Object> parseIntervalAndAggsType(Map<String, Object> aggs, String termInterval) {
        Map<String, Object> rtn = new HashMap<>();
        String interval = null;
        String aggType = null;
        if (aggs.size() == 1) {
            for (String aggsKey : aggs.keySet()) {
                Map<String, Object> firstDepthAggs = (Map<String, Object>) aggs.get(aggsKey);
                Map<String, Object> date_histogram = (Map<String, Object>) firstDepthAggs.get("date_histogram");
                Map<String, Object> terms = (Map<String, Object>) firstDepthAggs.get("terms");

                if (date_histogram != null) {
                    interval = (String) date_histogram.get("interval");
                    aggType = "date_histogram";
                    logger.info("interval = " + interval);
                }

                if (enableTermsCache && terms != null) {
                    interval = termInterval;
                    aggType = "terms";
                }
            }
        }
        rtn.put("interval", interval);
        rtn.put("aggsType", aggType);
        return rtn;
    }

    public Map<String, Object> getManipulateQuery(Map<String, Object> qMap, DateTime startDt, DateTime endDt) {
        Map<String, Object> map = (Map<String, Object>) SerializationUtils.clone((HashMap<String, Object>) qMap);
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        Map<String, Object> bool = (Map<String, Object>) query.get("bool");
        List<Map<String, Object>> must = (List<Map<String, Object>>) bool.get("must");
        for (Map<String, Object> obj : must) {
            Map<String, Object> range = (Map<String, Object>) obj.get("range");
            if (range != null) {
                Map<String, Object> ts = (Map<String, Object>) range.get(timeFiledName);
                ts.put("gte", startDt.getMillis());
                ts.put("lte", endDt.getMillis());
//                logger.info("ts = " + JsonUtil.convertAsString(ts));
            }
        }
        map.put("query", query);
        return map;
    }

    public List<DateHistogramBucket> getDhbList(String resBody) {
        List<DateHistogramBucket> dhbList = new ArrayList<>();
        Map<String, Object> resp = parsingService.parseXContent(resBody);

        Map<String, Object> aggrs = (Map<String, Object>) resp.get("aggregations");

        for (String aggKey : aggrs.keySet()) {
//                logger.info("aggKey = " + aggrs.get(aggKey));

            HashMap<String, Object> buckets = (HashMap<String, Object>) aggrs.get(aggKey);

            for (String bucketsKey : buckets.keySet()) {
                List<Map<String, Object>> bucketList = (List<Map<String, Object>>) buckets.get(bucketsKey);
                for (Map<String, Object> bucket : bucketList) {
                    String key_as_string = (String) bucket.get("key_as_string");

//                    logger.info("for key_as_string = " + key_as_string + " " + "key = " + bucket.get("key"));

                    try {
                        Long ts = (Long) bucket.get("key");
                        DateHistogramBucket dhb = new DateHistogramBucket(new DateTime(ts), bucket);
                        dhbList.add(dhb);
                    } catch (ClassCastException e) {
                        logger.info("debug info : " + resBody);
                        e.printStackTrace();
                    }
                }
            }
        }
        return dhbList;
    }

    public void putCache(String resBody, QueryPlan queryPlan) {
        if (queryPlan.getInterval() != null) {
            List<DateHistogramBucket> dhbList = getDhbList(resBody);
            if (queryPlan.getInterval() != null) {
                List<DateHistogramBucket> cacheDhbList = new ArrayList<>();
                for (DateHistogramBucket dhb : dhbList) {
                    logger.info("cache candiate : " + dhb.getDate() + " startDt : " + queryPlan.getCachePlan().getStartDt() + " endDt : " + queryPlan.getCachePlan().getEndDt());
                    if (cachePlanService.checkCacheable(queryPlan.getInterval(), dhb.getDate(), queryPlan.getCachePlan().getStartDt(), queryPlan.getCachePlan().getEndDt())) {
                        logger.info("cacheable");
                        cacheDhbList.add(dhb);
                    }
                }
                try {
                    if (cacheDhbList.size() > 0) {
                        cacheRepository.putCache(queryPlan.getIndexName(), queryPlan.getQueryWithoutRange(), queryPlan.getAggs(), cacheDhbList);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String generateTermsRes(String resBody) {
//        logger.info("generateTermsRes " + resBody);
        Map<String, Object> resp = parsingService.parseXContent(resBody);

        Map<String, Object> aggrs = (Map<String, Object>) resp.get("aggregations");

        Map<String, Object> mergedMap = new HashMap<>();
        String termsBucketKey = null;
        for (String aggKey : aggrs.keySet()) {
//            logger.info("aggKey = " + aggKey);

            HashMap<String, Object> buckets = (HashMap<String, Object>) aggrs.get(aggKey);

            for (String bucketsKey : buckets.keySet()) {
//                logger.info("bucketsKey = " + bucketsKey);
                List<Map<String, Object>> bucketList = (List<Map<String, Object>>) buckets.get(bucketsKey);
                for (Map<String, Object> dhBucket : bucketList) {
                    Map<String, Object> termsMap = null;
                    for (String dhBucketKey : dhBucket.keySet()) {
                        if (!"doc_count".equals(dhBucketKey) && !"key_as_string".equals(dhBucketKey) && !"key".equals(dhBucketKey)) {
                            termsBucketKey = dhBucketKey;
                            termsMap = (Map<String, Object>) dhBucket.get(dhBucketKey);
                        }
                    }

                    calculateRecursively(mergedMap, termsMap);
//                    logger.info("mergedMap = " + JsonUtil.convertAsString(mergedMap));
                }
            }
        }

//        logger.info("mergedBucket = " + JsonUtil.convertAsString(mergedBucket));

        aggrs = new HashMap<>();
        aggrs.put(termsBucketKey, mergedMap);
        resp.remove("aggregations");
        resp.put("aggregations", aggrs);
        String rtnBody = JsonUtil.convertAsString(resp);
//        logger.info("rtnBody = " + rtnBody);
        return JsonUtil.convertAsString(resp);
    }

    private void calculateRecursively(Map<String, Object> mergedMap, Map<String, Object> termsMap) {
        for (String key : termsMap.keySet()) {
            if (!mergedMap.containsKey(key)) {
                mergedMap.put(key, termsMap.get(key));
            } else if (termsMap.get(key) instanceof Map) {
                if (!mergedMap.containsKey(key)) {
                    mergedMap.put(key, new HashMap<String, Object>());
                }
                calculateRecursively((Map<String, Object>) mergedMap.get(key), (Map<String, Object>) termsMap.get(key));
            } else {
                if (mergedMap.get(key) instanceof Long || termsMap.get(key) instanceof Long) {
                    long newVal = Long.parseLong(mergedMap.get(key).toString()) + Long.parseLong(termsMap.get(key).toString());
                    mergedMap.put(key, newVal);
                } else if (mergedMap.get(key) instanceof Float || termsMap.get(key) instanceof Float) {
                    float newVal = Float.parseFloat(mergedMap.get(key).toString()) + Float.parseFloat(termsMap.get(key).toString());
                    mergedMap.put(key, newVal);
                } else if (mergedMap.get(key) instanceof Double || termsMap.get(key) instanceof Double) {
                    double newVal = Double.parseDouble(mergedMap.get(key).toString()) + Double.parseDouble(termsMap.get(key).toString());
                    mergedMap.put(key, newVal);
                } else if (mergedMap.get(key) instanceof Integer) {
                    int newVal = Integer.parseInt(mergedMap.get(key).toString()) + Integer.parseInt(termsMap.get(key).toString());
                    mergedMap.put(key, newVal);
                } else if (mergedMap.get(key) instanceof Short) {
                    int newVal = Short.parseShort(mergedMap.get(key).toString()) + Short.parseShort(termsMap.get(key).toString());
                    mergedMap.put(key, newVal);
                } else if (mergedMap.get(key) instanceof List) {
                    List<Map<String, Object>> bucketList = (List<Map<String, Object>>) termsMap.get(key);
                    List<Map<String, Object>> mergedBucketList = (List<Map<String, Object>>) mergedMap.get(key);
                    calculateList(mergedBucketList, bucketList);
                }
            }
        }
    }

    private void calculateList(List<Map<String, Object>> mergedBucketList, List<Map<String, Object>> bucketList) {
        for (Map<String, Object> bucket : bucketList) {
            boolean notExists = true;
            for (Map<String, Object> mergedBucket : mergedBucketList) {
                if (bucket.get("key").toString().equals(mergedBucket.get("key").toString())) {
                    notExists = false;
                    calculateRecursively(mergedBucket, bucket);
                }
            }
            if (notExists) {
                Map<String, Object> clonedBucket = (Map<String, Object>) SerializationUtils.clone(new HashMap<>(bucket));
                mergedBucketList.add(clonedBucket);
            }
        }
    }

    private String getIntervalTerms(String indexName, DateTime startDt, DateTime endDt) {
        if (indexName.contains("realtime")) {
            return "1m";
        } else if (Days.daysBetween(startDt, endDt).getDays() > 7) {
            return "1d";
        } else {
            return "1h";
        }
    }
}
