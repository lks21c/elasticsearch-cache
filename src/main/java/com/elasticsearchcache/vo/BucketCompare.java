package com.elasticsearchcache.vo;

import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class BucketCompare implements Comparator<HashMap<String, Object>> {
    private static final Logger logger = LogManager.getLogger(BucketCompare.class);

    @Override
    public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
        boolean isMetric = false;
        for (String key : o1.keySet()) {
            if (!"key".equals(key) && !"doc_count".equals(key)) {
                HashMap<String, Object> item1 = (HashMap<String, Object>) o1.get(key);
                if (item1.size() == 1 && item1.containsKey("value")) {
                    isMetric = true;
                }
            }
        }

        for (String key : o2.keySet()) {
            if (!"key".equals(key) && !"doc_count".equals(key)) {
                HashMap<String, Object> item2 = (HashMap<String, Object>) o2.get(key);
                if (item2.containsKey("buckets")) {
                    ArrayList<HashMap<String, Object>> bucket = (ArrayList<HashMap<String, Object>>) item2.get("buckets");
                    Collections.sort(bucket, new BucketCompare());
//                    logger.info("super compare #2 " + o2.get("key") + " " + JsonUtil.convertAsString(bucket));
                }
            }
        }

        if (isMetric) {
            for (String key : o1.keySet()) {
                if (!"key".equals(key) && !"doc_count".equals(key)) {
                    HashMap<String, Object> datapoint1 = (HashMap<String, Object>) o1.get(key);
                    HashMap<String, Object> datapoint2 = (HashMap<String, Object>) o2.get(key);

//                    logger.info("compare: " + o1.get("key") + " " + o2.get("key"));

                    if (Double.parseDouble(datapoint1.get("value").toString()) > Double.parseDouble(datapoint2.get("value").toString())) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        } else {
            if (Double.parseDouble(o1.get("doc_count").toString()) > Double.parseDouble(o2.get("doc_count").toString())) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
}
