package com.elasticsearchcache.performance;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PerformanceService {
    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    @Value("${esc.performance.enabled}")
    private boolean enablePerformance;

    @Value("${esc.performance.index.name}")
    private String esPerformanceName;

    @Value("${esc.profile.index.name}")
    private String esProfileName;

    @Value("${esc.cache.index.name}")
    private String esCacheName;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    private RestHighLevelClient restClient;

    public void putPerformance(String reqBody, int took) {
        if (enablePerformance && !reqBody.contains(".kibana")
                && !reqBody.contains(esPerformanceName)
                && !reqBody.contains(esProfileName)
                && !reqBody.contains(esCacheName)) {
            IndexRequest ir = new IndexRequest(esPerformanceName, "info");

            Map<String, Object> source = new HashMap<>();
            source.put("hostname", hostname);
            source.put("query", reqBody);
            source.put("latency", took);
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
    }
}
