package com.elasticsearchcache.performance;

import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.service.ElasticSearchService;
import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.IndexNameUtil;
import com.elasticsearchcache.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceService {
    private static final Logger logger = LogManager.getLogger(PerformanceService.class);

    @Value("${zuul.routes.*.url}")
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

    @Autowired
    private ElasticSearchService esService;

    public void putPerformance(String reqBody, int took) {
        if (enablePerformance && !reqBody.contains(".kibana")
                && !reqBody.contains(esPerformanceName)
                && !reqBody.contains(esProfileName)
                && !reqBody.contains(esCacheName)) {

            String[] arr = reqBody.split("\n");
            Map<String, Object> iMap = parsingService.parseXContent(arr[0]);

            List<String> idl = (List<String>) iMap.get("index");
//            logger.info("idl = " + JsonUtil.convertAsString(idl));
            String indexName = IndexNameUtil.getIndexName(idl);

//            logger.info("indexName = " + indexName);

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

    public String putDashboardCntAndReturnRes(String reqBody) {
        if (enablePerformance) {
            String resBody = null;
            try {
                HttpResponse res = esService.executeQuery(esUrl + EsUrl.SUFFIX_MULTI_GET, reqBody);
                resBody = EntityUtils.toString(res.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MethodNotSupportedException e) {
                e.printStackTrace();
            }

//            logger.info("resBody = " + resBody);

            Map<String, Object> reqMap = parsingService.parseXContent(reqBody);
            List<Map<String, Object>> reqMapDocs = (List<Map<String, Object>>) reqMap.get("docs");
            String type = null;
            if (reqMapDocs != null && reqMapDocs.size() > 0) {
                if (reqMapDocs.size() > 0) {
                    type = (String) reqMapDocs.get(0).get("_type");
                }
            }

            logger.info("type = " + type);

            Map<String, Object> map = parsingService.parseXContent(resBody);
            List<Map<String, Object>> docs = (List<Map<String, Object>>) map.get("docs");
            String title = null;
            if (docs != null && docs.size() > 0) {
                Map<String, Object> source = (Map<String, Object>) docs.get(0).get("_source");
                title = (String) source.get("title");
            }

            if ("dashboard".equals(type)) {
                IndexRequest ir = new IndexRequest(esPerformanceName, "info");

                Map<String, Object> source = new HashMap<>();
                source.put("hostname", hostname);
                source.put("dashboardName", title);
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
            return resBody;
        }
        return null;
    }
}
