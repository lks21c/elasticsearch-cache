package com.elasticsearchcache.performance;

import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.IndexNameUtil;
import com.elasticsearchcache.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceService {
    private static final Logger logger = LogManager.getLogger(PerformanceService.class);

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

    @Autowired
    private ParsingService parsingService;

    public void putPerformance(String reqBody, int took) {
        if (enablePerformance && !reqBody.contains(".kibana")
                && !reqBody.contains(esPerformanceName)
                && !reqBody.contains(esProfileName)
                && !reqBody.contains(esCacheName)) {

            String[] arr = reqBody.split("\n");
            Map<String, Object> iMap = parsingService.parseXContent(arr[0]);

            List<String> idl = (List<String>) iMap.get("index");
            logger.info("idl = " + JsonUtil.convertAsString(idl));
            String indexName = IndexNameUtil.getIndexName(idl);

            logger.info("indexName = " + indexName);

            IndexRequest ir = new IndexRequest(esPerformanceName, "info");

            Map<String, Object> source = new HashMap<>();
            source.put("hostname", hostname);
            source.put("query", reqBody);
            source.put("latency", took);
            source.put("ts", System.currentTimeMillis());
            source.put("indexName", indexName);
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
