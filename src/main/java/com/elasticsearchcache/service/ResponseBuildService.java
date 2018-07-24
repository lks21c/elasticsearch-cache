package com.elasticsearchcache.service;

import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.BucketCompare;
import com.elasticsearchcache.vo.DateHistogramBucket;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResponseBuildService {

    private static Logger logger = LoggerFactory.getLogger(ResponseBuildService.class);

    @Autowired
    ParsingService parsingService;

    public String generateRes(boolean typedKeys, List<DateHistogramBucket> dhbList, String aggsKey) {
        logger.info("typedKeys = " + typedKeys);
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
                "      \"aggregations\": {\n";

        if (typedKeys) {
            res += "        \"date_histogram#" + aggsKey + "\": {\n";
        } else {
            res += "        \"2\": {\n";
        }

        res += "          \"buckets\": \n";

        List<Map<String, Object>> buckets = new ArrayList<>();
        for (DateHistogramBucket bucket : dhbList) {
            buckets.add(bucket.getBucket());
        }
        res += JsonUtil.convertAsString(buckets);

        res += "            \n" +
                "        }\n" +
                "      }\n";

        if (!typedKeys) {
            res += "      ,\"status\": 200\n";
        }

        res += "    }\n";

        return res;
    }

    public String generateTermsRes(String resBody, List<Integer> termsSizeList) {
//        logger.info("generateTermsRes = " + resBody);
        Map<String, Object> resp = parsingService.parseXContent(resBody);

        Map<String, Object> aggrs = (Map<String, Object>) resp.get("aggregations");

        if (aggrs == null) {
            return resBody;
        }

        Map<String, Object> mergedMap = new HashMap<>();
        String termsBucketKey = null;
        for (String aggKey : aggrs.keySet()) {
//            logger.info("aggKey = " + aggKey);

            HashMap<String, Object> buckets = (HashMap<String, Object>) aggrs.get(aggKey);

            for (String bucketsKey : buckets.keySet()) {
//                logger.info("bucketsKey = " + bucketsKey);
                if ("buckets".equals(bucketsKey)) {
                    List<Map<String, Object>> bucketList = (List<Map<String, Object>>) buckets.get(bucketsKey);
                    for (Map<String, Object> dhBucket : bucketList) {
                        Map<String, Object> termsMap = null;
                        for (String dhBucketKey : dhBucket.keySet()) {
                            if (!"doc_count".equals(dhBucketKey) && !"key_as_string".equals(dhBucketKey) && !"key".equals(dhBucketKey)) {
                                termsBucketKey = dhBucketKey;
                                logger.debug("termsBucketKey = " + termsBucketKey);
                                termsMap = (Map<String, Object>) dhBucket.get(dhBucketKey);
                                break;
                            }
                        }

                        calculateRecursively(mergedMap, termsMap);
//                    logger.info("mergedMap = " + JsonUtil.convertAsString(mergedMap));
                    }
                }
            }
        }

//        logger.info("mergedMap = " + JsonUtil.convertAsString(mergedMap));
        ArrayList<HashMap<String, Object>> buckets = (ArrayList<HashMap<String, Object>>) mergedMap.get("buckets");

        Collections.sort(buckets, new BucketCompare(1, termsSizeList.size()));

        if (termsSizeList.size() >= 1) {
            while (buckets.size() > termsSizeList.get(0)) {
                buckets.remove(buckets.size() - 1);
            }
        }

        if (termsSizeList.size() > 1) {
            cutBucketSize(buckets, termsSizeList, 2);
        }

        mergedMap.put("buckets", buckets);

        logger.info("mergedMap() = " + JsonUtil.convertAsString(mergedMap) + " " + mergedMap.size());

        if (mergedMap.size() == 0)

        {
            return resBody;
        }

        aggrs = new HashMap<>();
        aggrs.put(termsBucketKey, mergedMap);
        resp.remove("aggregations");
        resp.put("aggregations", aggrs);
//        String rtnBody = JsonUtil.convertAsString(resp);
//        logger.info("rtnBody = " + rtnBody);
        return JsonUtil.convertAsString(resp);
    }

    private void cutBucketSize(ArrayList<HashMap<String, Object>> buckets, List<Integer> termsSizeList, int index) {

        for (int i = 0; i < buckets.size(); i++) {
            HashMap<String, Object> bucketItem = buckets.get(i);
            for (String key : bucketItem.keySet()) {
                if (bucketItem.get(key) instanceof HashMap) {
                    HashMap<String, Object> map = (HashMap<String, Object>) bucketItem.get(key);
                    if (map.containsKey("buckets")) {
                        ArrayList<HashMap<String, Object>> innerBucket = (ArrayList<HashMap<String, Object>>) map.get("buckets");
                        while (innerBucket.size() > termsSizeList.get(index - 1)) {
                            innerBucket.remove(innerBucket.size() - 1);
                        }
                        if (termsSizeList.size() > index) {
                            cutBucketSize(innerBucket, termsSizeList, index + 1);
                        }
                    }
                }
            }
        }
    }

    private void calculateRecursively(Map<String, Object> mergedMap, Map<String, Object> termsMap) {
//        logger.debug("mergedMap = " + JsonUtil.convertAsString(mergedMap));
//        logger.debug("termsMap = " + JsonUtil.convertAsString(termsMap));
        for (String key : termsMap.keySet()) {
//            logger.info("candidate key = " + mergedMap.get(key) + " " + termsMap.get(key));
            if (!mergedMap.containsKey(key)) {
//                logger.info("put key = " + key + " " + termsMap.get(key));
                mergedMap.put(key, termsMap.get(key));
            } else if (termsMap.get(key) instanceof Map) {
                if (!mergedMap.containsKey(key)) {
                    mergedMap.put(key, new HashMap<String, Object>());
                }
                calculateRecursively((Map<String, Object>) mergedMap.get(key), (Map<String, Object>) termsMap.get(key));
            } else {
                if (!"key".equals(key)) {
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
    }

    private void calculateList(List<Map<String, Object>> mergedBucketList, List<Map<String, Object>> bucketList) {
        for (Map<String, Object> bucket : bucketList) {
            logger.debug(" comparison start");
            boolean notExists = true;
            for (Map<String, Object> mergedBucket : mergedBucketList) {
                logger.debug("key comparison = " + bucket.get("key").toString() + " " + mergedBucket.get("key").toString());
                if (bucket.get("key").toString().equals(mergedBucket.get("key").toString())) {
                    logger.debug("key match = " + bucket.get("key").toString() + " " + mergedBucket.get("key").toString());
                    notExists = false;
                    calculateRecursively(mergedBucket, bucket);
                }
            }
            if (notExists) {
                logger.debug("key not match = " + JsonUtil.convertAsString(bucket) + "\n" + JsonUtil.convertAsString(mergedBucketList));
                Map<String, Object> clonedBucket = (Map<String, Object>) SerializationUtils.clone(new HashMap<>(bucket));
                mergedBucketList.add(clonedBucket);
            }
        }
    }

    public String buildMultiSearchQuery(Map<String, Object> iMap, Map<String, Object> qMap) {
        return JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(qMap) + "\n";
    }
}
