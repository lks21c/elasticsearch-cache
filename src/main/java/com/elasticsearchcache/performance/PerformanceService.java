package com.elasticsearchcache.performance;

import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.service.ElasticSearchService;
import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.IndexNameUtil;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.QueryPlan;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceService {
    private static final Logger logger = LogManager.getLogger(PerformanceService.class);

    @Value("${zuul.routes.**.url}")
    private String esUrl;

    @Value("${esc.performance.enabled}")
    private boolean enablePerformance;

    @Value("${esc.performance.index.name}")
    private String esPerformanceName;

    @Value("${esc.cache_coverage.index.name}")
    private String esCacheCoverageName;

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

    public void putPerformance(String targetUrl, String reqBody, int took) {
        if (enablePerformance && !reqBody.contains(".kibana")
                && !reqBody.contains(esPerformanceName)
                && !reqBody.contains(esProfileName)
                && !reqBody.contains(esCacheName)) {

//            logger.info("putPerformance " + reqBody);

            IndexRequest ir = new IndexRequest(esPerformanceName, "info");

            Map<String, Object> source = new HashMap<>();
            source.put("hostname", hostname);
            source.put("query", reqBody);
            source.put("latency", took);
            source.put("ts", System.currentTimeMillis());

            if (StringUtils.isEmpty(targetUrl)) {
                String[] arr = reqBody.split("\n");
                Map<String, Object> iMap = parsingService.parseXContent(arr[0]);

                logger.info("arr[0] = " + arr[0]);

                String indexName;
                try {
                    List<String> idl = (List<String>) iMap.get("index");

                    logger.info("idl = " + JsonUtil.convertAsString(idl));
                    indexName = IndexNameUtil.getIndexName(idl);

                } catch (Exception e) {
                    indexName = (String) iMap.get("index");
                }
                source.put("indexName", indexName);
            } else {
                String indexName = targetUrl.split("/")[3];
                source.put("indexName", indexName);
            }

            ir.source(source);

//            logger.info("before putPerformance = " + ir.toString());

            restClient.indexAsync(ir, new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    logger.info("after performance = " + indexResponse.toString());
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
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

    @Async
    public void putCacheCoverage(QueryPlan queryPlan) {
        logger.info("[performance]");
        logger.info(queryPlan.getIndexName());
        logger.info(queryPlan.getCachePlan().getCacheMode());
        if (queryPlan.getDhbList() != null) {
            logger.info(queryPlan.getDhbList().size());
        }
        logger.info(queryPlan.getInterval());
        logger.info(queryPlan.getAggsType());
        logger.info(queryPlan.isMultiSearch());

        DateTime startDt = new DateTime();
        DateTime endDt = new DateTime();
        if (queryPlan.getCachePlan().getStartDt() != null) {
            startDt = queryPlan.getCachePlan().getStartDt();
        }
        if (queryPlan.getCachePlan().getPreStartDt() != null) {
            startDt = queryPlan.getCachePlan().getPreStartDt();
        }
        if (queryPlan.getCachePlan().getEndDt() != null) {
            endDt = queryPlan.getCachePlan().getEndDt();
        }
        if (queryPlan.getCachePlan().getPostEndDt() != null) {
            endDt = queryPlan.getCachePlan().getPostEndDt();
        }

        int maxSize = 0;
        if ("1M".equals(queryPlan.getInterval())) {
            logger.info(Days.daysBetween(startDt, endDt).getDays() + 1);
            maxSize = Months.monthsBetween(startDt, endDt).getMonths() + 1;
        } else if ("1d".equals(queryPlan.getInterval().toLowerCase())) {
            logger.info(Days.daysBetween(startDt, endDt).getDays() + 1);
            maxSize = Days.daysBetween(startDt, endDt).getDays() + 1;
        } else if ("1h".equals(queryPlan.getInterval().toLowerCase())) {
            logger.info(Hours.hoursBetween(startDt, endDt).getHours() + 1);
            maxSize = Hours.hoursBetween(startDt, endDt).getHours() + 1;
        } else if ("1m".equals(queryPlan.getInterval().toLowerCase())) {
            logger.info(Minutes.minutesBetween(startDt, endDt).getMinutes() + 1);
            maxSize = Minutes.minutesBetween(startDt, endDt).getMinutes() + 1;
        }

        double coverage = 0;
        if (queryPlan.getDhbList() != null) {
            coverage = (double) queryPlan.getDhbList().size() / (double) maxSize * 100;
        }
        logger.info("coverage = " + coverage);

        logger.info("yes " + esCacheCoverageName);
        IndexRequest ir = new IndexRequest(esCacheCoverageName, "info");

        Map<String, Object> source = new HashMap<>();
        source.put("indexName", queryPlan.getIndexName());
        source.put("cacheMode", queryPlan.getCachePlan().getCacheMode());
        if (queryPlan.getDhbList() != null) {
            source.put("cacheSize", queryPlan.getDhbList().size());
        } else {
            source.put("cacheSize", 0);
        }

        source.put("maxSize", maxSize);
        source.put("coverage", coverage);
        source.put("interval", queryPlan.getInterval());
        source.put("aggsType", queryPlan.getAggsType());
        if (queryPlan.getDhbList() != null && queryPlan.getDhbList().size() > 0) {
            source.put("savedBytes", JsonUtil.convertAsString(queryPlan.getDhbList()).length());
        }
        source.put("isMultiSearch", queryPlan.isMultiSearch());
        source.put("ts", System.currentTimeMillis());
        ir.source(source);

        restClient.indexAsync(ir, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
