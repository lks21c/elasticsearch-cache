package com.elasticsearchcache.warmup;

import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.QueryExecService;
import com.elasticsearchcache.vo.QueryPlan;
import org.apache.http.MethodNotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarmUpService {
    private static final Logger logger = LogManager.getLogger(WarmUpService.class);

    @Autowired
    private RestHighLevelClient restClient;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private QueryExecService queryExecService;

    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    @Value("${esc.profile.index.name}")
    private String esProfileName;

    @Value("${esc.profile.enabled}")
    private boolean enableProfile;

    @Scheduled(fixedDelay = 60000)
    public void warmUpMinuteQueries() {
        logger.info("warmup invoked");

        SearchRequest sr = new SearchRequest(esProfileName).types("info");
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        TermQueryBuilder tq = QueryBuilders.termQuery("interval", "1m");
        ssb.query(tq);
        sr.source(ssb);
        SearchResponse resp = null;
        try {
            resp = restClient.search(sr);
            logger.info("resp = " + resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (resp != null) {
            List<QueryPlan> queryPlanList = new ArrayList<>();
            for (SearchHit hit : resp.getHits().getHits()) {
                String value = (String) hit.getSourceAsMap().get("value");

                if (value.contains("date_histogram")) {
                    DateTime startDt = new DateTime();
                    startDt = startDt.withSecondOfMinute(0);
                    startDt = startDt.withMillisOfSecond(0);
                    startDt = startDt.minusMinutes(20);

                    DateTime endDt = new DateTime();
                    logger.info("warmup startdt = " + startDt + " " + endDt);
                    value = value.replace("$$gte$$", String.valueOf(startDt.getMillis()));
                    value = value.replace("$$lte$$", String.valueOf(endDt.getMillis()));

                    try {
                        QueryPlan qp = cacheService.manipulateQuery(value);
                        queryPlanList.add(qp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MethodNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
//                logger.info("value = " + value);
            }
            queryExecService.executeQuery(esUrl + EsUrl.SUFFIX_MULTI_SEARCH, queryPlanList);

        }
    }
}
