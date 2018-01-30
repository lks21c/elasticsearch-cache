package com.elasticsearchcache.service;

import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.DateHistogramBucket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResponseBuildService {

    public String generateRes(boolean typedKeys, List<DateHistogramBucket> dhbList) {
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
            res += "        \"date_histogram#2\": {\n";
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

    public String buildMultiSearchQuery(Map<String, Object> iMap, Map<String, Object> qMap) {
        return JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(qMap) + "\n";
    }
}
