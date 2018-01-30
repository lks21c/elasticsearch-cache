package com.elasticsearchcache.service;

import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.performance.PerformanceService;
import com.elasticsearchcache.profile.ProfileService;
import com.elasticsearchcache.repository.CacheRepository;
import com.elasticsearchcache.util.IndexNameUtil;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.util.PeriodUtil;
import com.elasticsearchcache.vo.CachePlan;
import com.elasticsearchcache.vo.DateHistogramBucket;
import com.elasticsearchcache.vo.QueryPlan;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lks21c
 */
@Service
public class CacheService {
    private static final Logger logger = LogManager.getLogger(CacheService.class);

    @Autowired
    private ParsingService parsingService;

    @Autowired
    private CachePlanService cachePlanService;

    @Autowired
    private ElasticSearchService esService;

    @Autowired
    @Qualifier("EsCacheRepositoryImpl")
    private CacheRepository cacheRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ResponseBuildService responseBuildService;

    @Autowired
    private QueryExecService queryExecService;

    @Autowired
    private PerformanceService performanceService;

    @Value("${zuul.routes.**.url}")
    private String esUrl;

    @Value("${esc.cache.terms}")
    private boolean enableTermsCache;

    @Value("${filedname.time}")
    private String timeFiledName;

    @Value("${esc.cache}")
    private Boolean esCache;

    public QueryPlan manipulateQuery(boolean isMultiSearch, String requestUri, String mReqBody) throws IOException {
        logger.debug("mReqBody = " + mReqBody);

        Map<String, Object> qMap = null;

        if (isMultiSearch) {
            String[] arr = mReqBody.split("\n");
            qMap = parsingService.parseXContent(arr[1]);
        } else {
            qMap = parsingService.parseXContent(mReqBody);
        }

        long indexSize = -1; //getTotalIndexSize(idl);
        logger.debug("total index size = " + indexSize);

        // Parse Index Name
        String indexName = getIndexName(isMultiSearch, requestUri, mReqBody);
        logger.debug("indexName = " + indexName);
        Assert.notNull(indexName, "indexName should not be null.");

        // Get Query
        Map<String, Object> query = (Map<String, Object>) qMap.get("query");
        logger.debug("query = " + JsonUtil.convertAsString(query));

        // getQueryWithoutRange
        Map<String, Object> queryWithoutRange = parsingService.getQueryWithoutRange(query);

        // parse startDt, endDt
        Map<String, Object> dateMap = parsingService.parseStartEndDt(query);
        DateTime startDt = (DateTime) dateMap.get("startDt");
        DateTime endDt = (DateTime) dateMap.get("endDt");

        // Get aggs
        Map<String, Object> aggs = parsingService.getAggs(qMap);

        // Parse Interval
        Map<String, Object> rtnMap = parsingService.parseIntervalAndAggsType(aggs, getIntervalTerms(indexName, startDt, endDt));
        String interval = (String) rtnMap.get("interval");
        String aggsType = (String) rtnMap.get("aggsType");
        logger.debug("aggsType = " + aggsType);

        // Put Query Profile
        if (isMultiSearch) {
            Map<String, Object> iMap = getIMap(mReqBody);
            profileService.putQueryProfile(indexName, interval, iMap, qMap, queryWithoutRange);
        }

        // handle terms
        if (enableTermsCache && "terms".equals(aggsType)) {
            aggs = appendDateHistogramForTermsAggr(aggs, getIntervalTerms(indexName, startDt, endDt));
            qMap.put("aggs", aggs);
        }

        if (isMultiSearch) {
            Map<String, Object> iMap = getIMap(mReqBody);
            logger.debug("manipulated curl -X POST -L '" + esUrl + EsUrl.SUFFIX_MULTI_SEARCH + "' " + " --data '" + JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(qMap) + "\n" + "'");
        }

        CachePlan plan = cachePlanService.checkCachePlan(interval, startDt, endDt);
        logger.debug("before cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.debug("before cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.debug("before cachePlan getStartDt = " + plan.getStartDt());
        logger.debug("before cachePlan getEndDt = " + plan.getEndDt());
        logger.debug("before cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.debug("before cachePlan getPostEndDt = " + plan.getPostEndDt());

        QueryPlan queryPlan = new QueryPlan();
        queryPlan.setCachePlan((CachePlan) SerializationUtils.clone(plan));
        queryPlan.setInterval(interval);
        queryPlan.setIndexName(indexName);
        queryPlan.setIndexSize(indexSize);
        queryPlan.setQueryWithoutRange(JsonUtil.convertAsString(queryWithoutRange));
        queryPlan.setAggs(JsonUtil.convertAsString(aggs));
        queryPlan.setAggsType(aggsType);

        DateTime beforeCacheMills = new DateTime();
        List<DateHistogramBucket> dhbList = cacheRepository.getCache(indexName, indexSize, JsonUtil.convertAsString(queryWithoutRange), JsonUtil.convertAsString(aggs), plan.getStartDt(), plan.getEndDt());
        long afterCacheMills = new DateTime().getMillis() - beforeCacheMills.getMillis();

//        logger.mReqBody("dhbList = " + JsonUtil.convertAsString(dhbList));
        logger.info("afterCacheMills = " + afterCacheMills);

        plan = cachePlanService.checkCacheMode(interval, plan, dhbList);
        queryPlan.getCachePlan().setCacheMode(plan.getCacheMode());
        logger.debug("cacheMode = " + plan.getCacheMode() + " cache size : " + dhbList.size());
        logger.debug("after cachePlan getPreStartDt = " + plan.getPreStartDt());
        logger.debug("after cachePlan getPreEndDt = " + plan.getPreEndDt());
        logger.debug("after cachePlan getStartDt = " + plan.getStartDt());
        logger.debug("after cachePlan getEndDt = " + plan.getEndDt());
        logger.debug("after cachePlan getPostStartDt = " + plan.getPostStartDt());
        logger.debug("after cachePlan getPostEndDt = " + plan.getPostEndDt());

        if (CacheMode.ALL.equals(plan.getCacheMode())) {
            queryPlan.setDhbList(dhbList);
            return queryPlan;
        } else if (CacheMode.PARTIAL.equals(plan.getCacheMode())) {
            DateTime measureDt = new DateTime();
            // execute pre query
            if (plan.getPreStartDt() != null && plan.getPreEndDt() != null) {
                Map<String, Object> preQmap = getManipulateQuery(qMap, plan.getPreStartDt(), plan.getPreEndDt());
                if (isMultiSearch) {
                    Map<String, Object> iMap = getIMap(mReqBody);
                    queryPlan.setPreQuery(responseBuildService.buildMultiSearchQuery(iMap, preQmap));
                }
            }

            long afterPreQueryMills = new DateTime().getMillis() - measureDt.getMillis();
            if (afterPreQueryMills > 0) {
                logger.info("after pre query = " + afterPreQueryMills);
            }

            //dhbList
            queryPlan.setDhbList(dhbList);

            // execute post query
            if (plan.getPostStartDt() != null && plan.getPostEndDt() != null) {
                Map<String, Object> postQmap = getManipulateQuery(qMap, plan.getPostStartDt(), plan.getPostEndDt());
                if (isMultiSearch) {
                    Map<String, Object> iMap = getIMap(mReqBody);
                    queryPlan.setPostQuery(responseBuildService.buildMultiSearchQuery(iMap, postQmap));
                }
            }

            long afterPostQueryMills = new DateTime().getMillis() - measureDt.getMillis();
            if (afterPostQueryMills > 0) {
                logger.info("after post query = " + afterPostQueryMills);
            }

            return queryPlan;
        } else {
            logger.info("else, so original request invoked " + startDt.getSecondOfDay());
            if ("terms".equals(aggsType)) {
                if (isMultiSearch) {
                    Map<String, Object> iMap = getIMap(mReqBody);
                    queryPlan.setQuery(responseBuildService.buildMultiSearchQuery(iMap, qMap));
                }
            } else {
                if (isMultiSearch) {
                    queryPlan.setQuery(mReqBody);
                } else {
                    queryPlan.setQuery(mReqBody);
                }
            }
            return queryPlan;
        }
    }

    private String getIndexName(boolean isMultiSearch, String requestUri, String mReqBody) {
        if (isMultiSearch) {
            Map<String, Object> iMap = getIMap(mReqBody);
            List<String> idl = (List<String>) iMap.get("index");
            logger.info("idl = " + JsonUtil.convertAsString(idl));
            return IndexNameUtil.getIndexName(idl);
        } else {
            List<String> idl = Arrays.asList(requestUri.replace("/", "").split(","));
            return IndexNameUtil.getIndexName(idl);
        }
    }

    private long getTotalIndexSize(List<String> idl) {
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        for (String indexName : idl) {
            sb.append(indexName);
            if (!isFirst) {
                sb.append(",");
            }
            isFirst = false;
        }

        long totalSize = 0;
        try {
            HttpResponse res = esService.executeHttpRequest(HttpMethod.GET, esUrl + "/_stats", new ByteArrayEntity("".getBytes()));
            String body = EntityUtils.toString(res.getEntity());
            String[] sizeArr = body.split("count");
            String size = sizeArr[1].split(",")[0].replace("\":", "");
            totalSize = Long.valueOf(size);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MethodNotSupportedException e) {
            e.printStackTrace();
        }

        return totalSize;
    }

    private Map<String, Object> appendDateHistogramForTermsAggr(Map<String, Object> aggs, String interval) {
        HashMap<String, Object> hashMap = new HashMap<>(aggs);
        HashMap<String, Object> newAggs = new HashMap<>();
        Map<String, Object> clonedAggs = (Map<String, Object>) SerializationUtils.clone(hashMap);

        HashMap<String, Object> dtEntry = new HashMap<>();
        dtEntry.put("field", timeFiledName);
        dtEntry.put("interval", interval);
        dtEntry.put("time_zone", PeriodUtil.getTimeZone());

        HashMap<String, Object> dtMap = new HashMap<>();
        dtMap.put("date_histogram", dtEntry);
        dtMap.put("aggs", clonedAggs);

        newAggs.put("dates", dtMap);
        return newAggs;
    }

    public Map<String, Object> getManipulateQuery(Map<String, Object> qMap, DateTime startDt, DateTime endDt) {
        Map<String, Object> map = (Map<String, Object>) SerializationUtils.clone((HashMap<String, Object>) qMap);
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        Map<String, Object> bool = (Map<String, Object>) query.get("bool");
        List<Map<String, Object>> must = (List<Map<String, Object>>) bool.get("must");
        for (Map<String, Object> obj : must) {
            Map<String, Object> range = (Map<String, Object>) obj.get("range");
            if (range != null) {
                Map<String, Object> ts = (Map<String, Object>) range.get(timeFiledName);
                ts.put("gte", startDt.getMillis());
                ts.put("lte", endDt.getMillis());
//                logger.info("ts = " + JsonUtil.convertAsString(ts));
            }
        }
        map.put("query", query);
        return map;
    }

    public void putCache(String resBody, QueryPlan queryPlan) {
        if (queryPlan.getInterval() != null) {
            List<DateHistogramBucket> dhbList = parsingService.getDhbList(resBody);
            if (queryPlan.getInterval() != null) {
                List<DateHistogramBucket> cacheDhbList = new ArrayList<>();
                for (DateHistogramBucket dhb : dhbList) {
                    logger.debug("cache candiate : " + queryPlan.getInterval() + " " + dhb.getDate() + " startDt : " + queryPlan.getCachePlan().getStartDt() + " endDt : " + queryPlan.getCachePlan().getEndDt());
                    boolean cacheable = cachePlanService.checkCacheable(queryPlan.getInterval(), dhb.getDate(), queryPlan.getCachePlan().getStartDt(), queryPlan.getCachePlan().getEndDt());
                    logger.debug("checkCacheable = " + cacheable);
                    if (cacheable) {
                        logger.debug("cacheable");
                        cacheDhbList.add(dhb);
                    }
                }
                try {
                    if (cacheDhbList.size() > 0) {
                        cacheRepository.putCache(queryPlan.getIndexName(), queryPlan.getIndexSize(), queryPlan.getQueryWithoutRange(), queryPlan.getAggs(), cacheDhbList);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getIntervalTerms(String indexName, DateTime startDt, DateTime endDt) {
        if (indexName.contains("realtime")) {
            return "1m";
        } else if (Days.daysBetween(startDt, endDt).getDays() > 7) {
            return "1d";
        } else {
            return "1h";
        }
    }

    private Map<String, Object> getIMap(String mReqBody) {
        String[] arr = mReqBody.split("\n");
        Map<String, Object> iMap = parsingService.parseXContent(arr[0]);
        return iMap;
    }

    public StringBuilder executeMultiSearch(String targetUrl, String reqBody) throws IOException, MethodNotSupportedException {
        StringBuilder sb = new StringBuilder();
        String[] reqs = reqBody.split("\n");
        if (esCache && !reqBody.contains(".kibana")) {
            long beforeQueries = System.currentTimeMillis();
            List<QueryPlan> queryPlanList = new ArrayList<>();
            for (int i = 0; i < reqs.length; i = i + 2) {
                QueryPlan queryPlan = manipulateQuery(true, null, reqs[i] + "\n" + reqs[i + 1] + "\n");
//                            logger.info("queryPlan = " + JsonUtil.convertAsString(queryPlan));
                queryPlanList.add(queryPlan);
            }
            String body = queryExecService.executeQuery(true, targetUrl, queryPlanList);
            if (StringUtils.isEmpty(body)) {
                logger.info("esc cancelled.");
                long afterQueries = System.currentTimeMillis() - beforeQueries;
                logger.info("cache afterQueries = " + afterQueries);
                performanceService.putPerformance(reqBody, (int) afterQueries);
                return null;
            }
            sb.append(body);
            long afterQueries = System.currentTimeMillis() - beforeQueries;
            logger.info("cache afterQueries = " + afterQueries);
            performanceService.putPerformance(reqBody, (int) afterQueries);
        } else {
            long beforeQueries = System.currentTimeMillis();
            HttpResponse res = esService.executeQuery(targetUrl, reqBody);
            String resBody = EntityUtils.toString(res.getEntity());
            Map<String, Object> resMap = parsingService.parseXContent(resBody);
            List<Map<String, Object>> respes = (List<Map<String, Object>>) resMap.get("responses");
            if (respes.size() > 0 && !reqBody.contains(".kibana")) {
                int took = (int) respes.get(0).get("took");
                logger.info("took = " + took);
            }
            sb.append(resBody);
            long afterQueries = System.currentTimeMillis() - beforeQueries;
            logger.info("nocache afterQueries = " + afterQueries);
            performanceService.putPerformance(reqBody, (int) afterQueries);
        }
        return sb;
    }

    public StringBuilder executeSearch(String targetUrl, String requestURI, String reqBody) {
        StringBuilder sb = new StringBuilder();
        if (esCache && !reqBody.contains(".kibana")) {
            long beforeQueries = System.currentTimeMillis();
            List<QueryPlan> queryPlanList = new ArrayList<>();
            try {
                QueryPlan queryPlan = manipulateQuery(false, requestURI, reqBody);
                queryPlanList.add(queryPlan);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String body = queryExecService.executeQuery(false, targetUrl, queryPlanList);
            if (StringUtils.isEmpty(body)) {
                logger.info("esc cancelled.");
                return null;
            }
            sb.append(body);
            long afterQueries = System.currentTimeMillis() - beforeQueries;
            logger.info("cache afterQueries = " + afterQueries);
        } else {
            long beforeQueries = System.currentTimeMillis();
            HttpResponse res = null;
            try {
                res = esService.executeQuery(targetUrl, reqBody);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MethodNotSupportedException e) {
                e.printStackTrace();
            }
            String resBody = null;
            try {
                resBody = EntityUtils.toString(res.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> respes = parsingService.parseResponses(resBody);
            if (respes.size() > 0 && !reqBody.contains(".kibana")) {
                int took = (int) respes.get(0).get("took");
                logger.info("took = " + took);
            }
            sb.append(resBody);
            long afterQueries = System.currentTimeMillis() - beforeQueries;
            logger.info("nocache afterQueries = " + afterQueries);
        }
        return sb;
    }
}
