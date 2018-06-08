package com.elasticsearchcache.warmup;

import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.QueryExecService;
import com.elasticsearchcache.vo.QueryPlan;
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

    @Value("${zuul.routes.**.url}")
    private String esUrl;

    @Value("${esc.profile.index.name}")
    private String esProfileName;

    @Value("${esc.warmup}")
    private boolean escWarmUp;

    @Value("${esc.cache}")
    private boolean escCache;

    @Value("${esc.cache.terms}")
    private boolean escTerms;

    @Value("${esc.cache.warmup.size}")
    private int esWarmUpSize;

    @Scheduled(fixedDelay = 1000 * 60 * 2)
    public void warmUpMinuteQueries() {
        if (escCache && escWarmUp) {
            DateTime startDt = new DateTime();
            startDt = startDt.withSecondOfMinute(0);
            startDt = startDt.withMillisOfSecond(0);
            startDt = startDt.minusMinutes(20);
            DateTime endDt = new DateTime();
            warmUp("1m", startDt, endDt);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void warmUpHourQueries() {
        if (escCache && escWarmUp) {
            DateTime startDt = new DateTime();
            startDt = startDt.withSecondOfMinute(0);
            startDt = startDt.withMillisOfSecond(0);
            startDt = startDt.withMinuteOfHour(0);
            startDt = startDt.withHourOfDay(0);
            DateTime endDt = startDt.plusDays(1).minus(1);
            startDt = startDt.minusDays(1);
            warmUp("1h", startDt, endDt);

            startDt = startDt.minusDays(2);
            endDt = endDt.minusDays(2);
            warmUp("1h", startDt, endDt);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 120)
    public void warmUpDayQueries() {
        if (escCache && escWarmUp) {
            DateTime startDt = new DateTime();
            startDt = startDt.withSecondOfMinute(0);
            startDt = startDt.withMillisOfSecond(0);
            startDt = startDt.withMinuteOfHour(0);
            startDt = startDt.withHourOfDay(0);
            DateTime endDt = startDt.plusDays(1).minus(1);
            startDt = startDt.minusDays(3);
            warmUp("1d", startDt, endDt);

            startDt = startDt.minusDays(4);
            endDt = endDt.minusDays(4);
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
        ssb.size(10000);
        sr.source(ssb);

//        logger.info("SearchRequest = " + sr.toString());

        SearchResponse resp = null;
        try {
            resp = restClient.search(sr);
//            logger.info("resp = " + resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (resp != null) {
            List<QueryPlan> queryPlanList = new ArrayList<>();
            logger.info("warm candiate size = " + resp.getHits().getHits().length);
            for (SearchHit hit : resp.getHits().getHits()) {
                String value = (String) hit.getSourceAsMap().get("value");

                if (value.contains("date_histogram")
                        || (escTerms && value.contains("terms") && !value.contains("cardinality"))) {
                    logger.info("warmup startdt = " + startDt + " " + endDt);
                    value = value.replace("$$gte$$", String.valueOf(startDt.getMillis()));
                    value = value.replace("$$lte$$", String.valueOf(endDt.getMillis()));

                    try {
                        QueryPlan qp = cacheService.manipulateQuery(true, null, value);
                        queryPlanList.add(qp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("value = " + value);

                if (queryPlanList.size() == esWarmUpSize) {
                    queryExecService.executeQuery(true, esUrl + EsUrl.SUFFIX_MULTI_SEARCH, queryPlanList);
                    queryPlanList = new ArrayList<>();
                }
            }
            logger.info("queryPlanList size = " + queryPlanList.size());
            if (queryPlanList.size() > 0) {
                queryExecService.executeQuery(true, esUrl + EsUrl.SUFFIX_MULTI_SEARCH, queryPlanList);
            }

            long endWarmUpTs = System.currentTimeMillis() - startWarmUpTs;
            logger.info("endWarmUpTs = " + endWarmUpTs);
        }

    }
}
