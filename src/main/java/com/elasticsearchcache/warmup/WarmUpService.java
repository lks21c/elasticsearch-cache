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

    @Value("${esc.warmup}")
    private boolean escWarmUp;

    @Value("${esc.cache.warmup.size}")
    private int esWarmUpSize;

    @Scheduled(fixedDelay = 1000 * 60 * 2)
    public void warmUpMinuteQueries() {
        if (escWarmUp) {
            DateTime startDt = new DateTime();
            startDt = startDt.withSecondOfMinute(0);
            startDt = startDt.withMillisOfSecond(0);
            startDt = startDt.minusMinutes(20);
            DateTime endDt = new DateTime();
            warmUp("1m", startDt, endDt);
        }
    }

    @Scheduled(cron = "0 0 01 * * ?")
    public void warmUpDayQueries() {
        if (escWarmUp) {
            DateTime startDt = new DateTime();
            startDt = startDt.withSecondOfMinute(0);
            startDt = startDt.withMillisOfSecond(0);
            startDt = startDt.withMinuteOfHour(0);
            startDt = startDt.withHourOfDay(0);
            DateTime endDt = startDt.plusDays(1).minus(1);
            startDt = startDt.minusDays(7);

            warmUp("1d", startDt, endDt);
        }
    }

    private void warmUp(String interval, DateTime startDt, DateTime endDt) {

        logger.info("warmup invoked");

        long startWarmUpTs = System.currentTimeMillis();

        SearchRequest sr = new SearchRequest(esProfileName).types("info");
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        TermQueryBuilder tq = QueryBuilders.termQuery("interval", interval);
        ssb.query(tq);
        ssb.size(1000);
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

                if (queryPlanList.size() == esWarmUpSize) {
                    queryExecService.executeQuery(esUrl + EsUrl.SUFFIX_MULTI_SEARCH, queryPlanList);
                    queryPlanList = new ArrayList<>();
                }
            }
            logger.info("queryPlanList size = " + queryPlanList.size());
            if (queryPlanList.size() > 0) {
                queryExecService.executeQuery(esUrl + EsUrl.SUFFIX_MULTI_SEARCH, queryPlanList);
            }

            long endWarmUpTs = System.currentTimeMillis() - startWarmUpTs;
            logger.info("endWarmUpTs = " + endWarmUpTs);
        }

    }
}
