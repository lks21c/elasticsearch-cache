package com.elasticsearchcache;

import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.conts.HttpMethod;
import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.ElasticSearchService;
import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.service.QueryPlanService;
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
import java.util.List;
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
    ParsingService parsingService;

    @Autowired
    CacheService cacheService;

    @Autowired
    private QueryPlanService queryPlanService;

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
            String targetUrl = getTargetUrl(request);
            logger.info("request = " + targetUrl);

            StringBuilder sb = new StringBuilder();
            if (HttpMethod.POST.equals(request.getMethod())) {
                String reqBody = getRequestBody(request);

                logger.info("original curl -X POST -L '" + targetUrl + "' " + " --data '" + reqBody + "'");
                logger.info("original reqBody = " + reqBody);

                if (request.getRequestURI().contains(EsUrl.SUFFIX_MULTI_SEARCH)) {
                    handleRequestHeader(request);

                    String[] reqs = reqBody.split("\n");
                    if (esCache && !reqBody.contains(".kibana")) {
                        long beforeQueries = System.currentTimeMillis();
                        List<QueryPlan> queryPlanList = new ArrayList<>();
                        for (int i = 0; i < reqs.length; i = i + 2) {
                            QueryPlan queryPlan = cacheService.manipulateQuery(reqs[i] + "\n" + reqs[i + 1] + "\n");
//                            logger.info("queryPlan = " + JsonUtil.convertAsString(queryPlan));
                            queryPlanList.add(queryPlan);
                        }
                        sb.append(queryPlanService.executeQuery(targetUrl, queryPlanList));
                        long afterQueries = System.currentTimeMillis() - beforeQueries;
                        logger.info("afterQueries = " + afterQueries);
                    } else {
                        long beforeQueries = System.currentTimeMillis();
                        HttpResponse res = esService.executeQuery(targetUrl, reqBody);
                        sb.append(EntityUtils.toString(res.getEntity()));
                        long afterQueries = System.currentTimeMillis() - beforeQueries;
                        logger.info("afterQueries = " + afterQueries);
                    }

                    if (sb.length() > 0) {
                        logger.info("sc ok ");
                        setZuulResponse(ctx, sb.toString());
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
        if (request != null) {
            return request.getReader().lines().collect(Collectors.joining(System.lineSeparator())) + "\n";
        }
        return null;
    }

    public String getTargetUrl(HttpServletRequest request) {
        String suffixUrl;
        if (!StringUtils.isEmpty(request.getQueryString())) {
            suffixUrl = request.getRequestURI().replace("/" + PROXY, "") + "?" + request.getQueryString();
        } else {
            suffixUrl = request.getRequestURI().replace("/" + PROXY, "");
        }
        return esUrl + suffixUrl;
    }

    public void setZuulResponse(RequestContext ctx, String responseBody) {
        ctx.addZuulResponseHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        ctx.addZuulResponseHeader(HttpHeaders.VARY, "Accept-Encoding");
        ctx.addZuulResponseHeader(HttpHeaders.CONNECTION, "Keep-Alive");
        ctx.setResponseStatusCode(HttpStatus.SC_OK);
        ctx.setResponseBody(responseBody);
        ctx.setSendZuulResponse(false);
    }

    private void handleRequestHeader(HttpServletRequest request) {
        //                    Enumeration<String> headers = request.getHeaderNames();
//                    while (headers.hasMoreElements()) {
//                        String header = headers.nextElement();
//                        System.out.println("header = " + header + " " + request.getHeader(header));
//                    }

    }

}