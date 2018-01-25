package com.elasticsearchcache.profile;

import com.elasticsearchcache.util.JsonUtil;
import org.apache.commons.lang.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.hash.MurmurHash3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfileService {
    private static final Logger logger = LogManager.getLogger(ProfileService.class);

    @Autowired
    private RestHighLevelClient restClient;

    @Value("${esc.profile.index.name}")
    private String esProfileName;

    @Value("${esc.profile.enabled}")
    private boolean enableProfile;

    public void putQueryProfile(String indexName, String interval, Map<String, Object> imap, Map<String, Object> qMap, Map<String, Object> queryWithoutRange) {
        if (enableProfile) {
            logger.debug("putQueryProfile");
            HashMap<String, Object> clonedQMap = (HashMap<String, Object>) SerializationUtils.clone(new HashMap<>(qMap));
            Map<String, Object> query = (Map<String, Object>) clonedQMap.get("query");

            Map<String, Object> bool = (Map<String, Object>) query.get("bool");
            List<Map<String, Object>> must = (List<Map<String, Object>>) bool.get("must");

            long gte = -1L, lte = -1L;
            for (Map<String, Object> obj : must) {
                Map<String, Object> range = (Map<String, Object>) obj.get("range");
                if (range != null) {
                    for (String rangeKey : range.keySet()) {
                        gte = (Long) ((Map<String, Object>) range.get(rangeKey)).get("gte");
                        lte = (Long) ((Map<String, Object>) range.get(rangeKey)).get("lte");
                    }
                }
            }
            Map<String, Object> aggs = (Map<String, Object>) clonedQMap.get("aggs");

            String key = indexName + JsonUtil.convertAsString(queryWithoutRange) + JsonUtil.convertAsString(aggs);

//            logger.info("profile key =  " + key);

            MurmurHash3.Hash128 hash = MurmurHash3.hash128(key.getBytes(), 0, key.getBytes().length, 0, new MurmurHash3.Hash128());
            String id = String.valueOf(hash.h1) + String.valueOf(hash.h2);

            String iMapStr = JsonUtil.convertAsString(imap);
            String qMapStr = JsonUtil.convertAsString(clonedQMap);
            if (gte > 0) {
                qMapStr = qMapStr.replace(String.valueOf(gte), "$$gte$$");
            }
            if (lte > 0) {
                qMapStr = qMapStr.replace(String.valueOf(lte), "$$lte$$");
            }

            IndexRequest ir = new IndexRequest(esProfileName, "info", id);
            Map<String, Object> source = new HashMap<>();
            source.put("key", "key");
            source.put("value", iMapStr + "\n" + qMapStr + "\n");
            source.put("interval", interval);
            source.put("ts", System.currentTimeMillis());
            ir.source(source);

            restClient.indexAsync(ir, new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
//        logger.info("profile query = " + iMapStr + "\n" + qMapStr);
//        logger.info("profile key = " + key);
    }
}
