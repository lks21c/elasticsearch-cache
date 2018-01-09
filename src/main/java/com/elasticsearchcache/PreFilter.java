package com.elasticsearchcache;

import com.elasticsearchcache.conts.CacheMode;
import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.ElasticSearchService;
import com.elasticsearchcache.service.NativeParsingServiceImpl;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.CachePlan;
import com.elasticsearchcache.vo.DateHistogramBucket;
import com.elasticsearchcache.vo.QueryPlan;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lks21c
 */
@Service
public class PreFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(PreFilter.class);

    @Autowired
    ElasticSearchService esService;

    @Autowired
    NativeParsingServiceImpl parsingService;

    @Autowired
    CacheService cacheService;

    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    @Value("${esc.cache}")
    private Boolean esCache;

    private static final String PROXY = "proxy";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //TODO: 임시로 처리, 지울 예정
        if (request.getRequestURI().equals("/proxy/.kibana/config/_search") ||
                request.getRequestURI().equals("/proxy/_mget") ||
                request.getRequestURI().equals("/proxy/_cluster/settings") ||
                request.getRequestURI().equals("/proxy/_nodes/_local") ||
                request.getRequestURI().equals("/proxy/_nodes") ||
                request.getRequestURI().equals("/proxy/") ||
                request.getRequestURI().equals("/proxy/_mapping") ||
                request.getRequestURI().equals("/proxy/_aliases") ||
                request.getRequestURI().equals("/proxy/_cluster/health/.kibana")) {
            return null;
        }

        try {
            String url;
            if (!StringUtils.isEmpty(request.getQueryString())) {
                url = request.getRequestURI().replace("/" + PROXY, "") + "?" + request.getQueryString();
            } else {
                url = request.getRequestURI().replace("/" + PROXY, "");
            }
            String targetUrl = esUrl + url;
            logger.info("request = " + targetUrl);

            if ("POST".equals(request.getMethod())) {
                String reqBody = getRequestBody(request);

                logger.info("original curl -X POST -L '" + targetUrl + "' " + " --data '" + reqBody + "'");

                logger.info("reqBody = " + reqBody);

                if (request.getRequestURI().contains("/_msearch")) {
                    Enumeration<String> headers = request.getHeaderNames();
                    while (headers.hasMoreElements()) {
                        String header = headers.nextElement();
                        System.out.println("header = " + header + " " + request.getHeader(header));
                    }

                    String[] reqs = reqBody.split("\n");

                    StringBuilder sb = new StringBuilder();
                    if (esCache && !reqBody.contains(".kibana")) {
                        long beforeQueries = System.currentTimeMillis();
                        List<Map<String, Object>> rb = new ArrayList<>();
                        List<QueryPlan> queryPlanList = new ArrayList<>();
                        for (int i = 0; i < reqs.length; i = i + 2) {
                            QueryPlan queryPlan = cacheService.manipulateQuery(reqs[i] + "\n" + reqs[i + 1] + "\n");
                            logger.info("queryPlan = " + JsonUtil.convertAsString(queryPlan));
                            queryPlanList.add(queryPlan);
                        }

                        StringBuilder qb = new StringBuilder();
                        for (QueryPlan qp : queryPlanList) {
                            if (!StringUtils.isEmpty(qp.getPreQuery())) {
                                qb.append(qp.getPreQuery());
                            }
                            if (!StringUtils.isEmpty(qp.getQuery())) {
                                qb.append(qp.getQuery());
                            }
                            if (!StringUtils.isEmpty(qp.getPostQuery())) {
                                qb.append(qp.getPostQuery());
                            }
                        }

                        HttpResponse res = esService.executeQuery(targetUrl, qb.toString());
                        String bulkRes = EntityUtils.toString(res.getEntity());
                        logger.info("refactor res = " + bulkRes);

                        Map<String, Object> resMap = parsingService.parseXContent(bulkRes);
                        List<Map<String, Object>> respes = (List<Map<String, Object>>) resMap.get("responses");

                        int responseCnt = 0;
                        for (int i = 0; i < queryPlanList.size(); i++) {
                            if (CacheMode.ALL.equals(queryPlanList.get(i).getCacheMode())) {
                                String body = cacheService.generateRes(queryPlanList.get(i).getDhbList());
                            } else if (CacheMode.PARTIAL.equals(queryPlanList.get(i).getCacheMode())) {
                                List<DateHistogramBucket> mergedDhbList = new ArrayList<>();
                                List<DateHistogramBucket> preDhbList;
                                if (!StringUtils.isEmpty(queryPlanList.get(i).getPreQuery())) {
                                    preDhbList = cacheService.getDhbList(JsonUtil.convertAsString(respes.get(responseCnt++)));
                                    mergedDhbList.addAll(preDhbList);
                                }

                                mergedDhbList.addAll(queryPlanList.get(i).getDhbList());

                                List<DateHistogramBucket> postDhbList;
                                if (!StringUtils.isEmpty(queryPlanList.get(i).getPostQuery())) {
                                    postDhbList = cacheService.getDhbList(JsonUtil.convertAsString(respes.get(responseCnt++)));
                                    mergedDhbList.addAll(postDhbList);
                                }
                                String body = cacheService.generateRes(mergedDhbList);
                            } else {
                                if (!StringUtils.isEmpty(queryPlanList.get(i).getQuery())) {
                                    respes.get(responseCnt++);
                                }
                            }
                        }

                        //responses parsing
                        Map<String, Object> mergedRes = new HashMap<>();
                        mergedRes.put("responses", rb);
                        sb.append(JsonUtil.convertAsString(mergedRes));

                        long afterQueries = System.currentTimeMillis() - beforeQueries;
                        logger.info("afterQueries = " + afterQueries);

                        res = esService.executeQuery(targetUrl, reqBody);
                        sb = new StringBuilder();
                        sb.append(EntityUtils.toString(res.getEntity()));
                    } else {
                        HttpResponse res = esService.executeQuery(targetUrl, reqBody);
                        sb.append(EntityUtils.toString(res.getEntity()));
                    }

                    if (sb.length() > 0) {
                        logger.info("sc ok ");
//                        logger.info("resBody = " + sb.toString());
                        ctx.addZuulResponseHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
                        ctx.addZuulResponseHeader(HttpHeaders.VARY, "Accept-Encoding");
                        ctx.addZuulResponseHeader(HttpHeaders.CONNECTION, "Keep-Alive");
                        ctx.setResponseStatusCode(HttpStatus.SC_OK);
                        ctx.setResponseBody(sb.toString());
                        ctx.setSendZuulResponse(false);
                    } else {
                        logger.error("original request invoked.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator())) + "\n";
    }
}

//    Map<String, Object> resMap = parsingService.parseXContent(body);
//    List<Map<String, Object>> responses = (List<Map<String, Object>>) resMap.get("responses");
//                            rb.add(responses.get(0));

//
//// Cacheable
//            if (interval != null) {
//                    List<DateHistogramBucket> originalDhbList = getDhbList(body);
//        List<DateHistogramBucket> cacheDhbList = new ArrayList<>();
//        for (DateHistogramBucket dhb : originalDhbList) {
//        if (cachePlanService.checkCacheable(interval, dhb.getDate(), startDt, endDt)) {
//        cacheDhbList.add(dhb);
//        }
//        }
//        cacheRepository.putCache(indexName, JsonUtil.convertAsString(queryWithoutRange), JsonUtil.convertAsString(aggs), cacheDhbList);
//        }
//
//
//        // execute pre query
//        if (plan.getPreStartDt() != null && plan.getPreEndDt() != null) {
//        Map<String, Object> preQmap = getManipulateQuery(qMap, plan.getPreStartDt(), plan.getPreEndDt());
//        String body = esService.getRequestBody(esUrl + "/_msearch", JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(preQmap) + "\n");
//        List<DateHistogramBucket> preDhbList = getDhbList(body);
//        for (DateHistogramBucket dhb : preDhbList) {
//        if (dhb.getBucket() != null) {
//        mergedDhb.add(dhb);
//        }
//        }
//        }
//
//
//        // execute post query
//        if (plan.getPostStartDt() != null && plan.getPostEndDt() != null) {
//        Map<String, Object> postQmap = getManipulateQuery(qMap, plan.getPostStartDt(), plan.getPostEndDt());
//        logger.info("post query = " + JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(postQmap) + "\n");
//        String body = esService.getRequestBody(esUrl + "/_msearch", JsonUtil.convertAsString(iMap) + "\n" + JsonUtil.convertAsString(postQmap) + "\n");
//        List<DateHistogramBucket> postDhbList = getDhbList(body);
//        for (DateHistogramBucket dhb : postDhbList) {
//        if (dhb.getBucket() != null) {
//        mergedDhb.add(dhb);
//        }
//        }
//        }