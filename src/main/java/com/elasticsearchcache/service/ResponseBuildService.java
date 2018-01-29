package com.elasticsearchcache.service;

import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.DateHistogramBucket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResponseBuildService {

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
}