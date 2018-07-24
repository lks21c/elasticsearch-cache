package com.elasticsearchcache;

import com.elasticsearchcache.conts.EsUrl;
import com.elasticsearchcache.conts.HttpMethod;
import com.elasticsearchcache.performance.PerformanceService;
import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.ElasticSearchService;
import com.elasticsearchcache.service.ParsingService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private PerformanceService performanceService;

    @Value("${zuul.routes.**.url}")
    private String esUrl;

    @Value("${esc.performance.enabled}")
    private boolean enablePerformance;
    private boolean skipUrl;

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

        //TODO: it's temporarily skip.
        if (isSkipUrl(request)) {
            return null;
        }

        try {
            String targetUrl = getTargetUrl(request);
            logger.info("request = " + targetUrl);
            if (HttpMethod.POST.equals(request.getMethod())) {
                if (request.getRequestURI().contains(EsUrl.SUFFIX_MULTI_SEARCH)) {
                    String reqBody = getRequestBody(request);
                    logger.info("original curl -X POST -L '" + targetUrl + "' " + " --data '" + reqBody + "'");
                    StringBuilder sb = cacheService.executeMultiSearch(targetUrl, reqBody);
                    if (sb.length() > 0) {
                        logger.info("sc ok ");
                        setZuulResponse(ctx, sb.toString());
                    } else {
                        logger.error("original request invoked.");
                    }
                } else if (request.getRequestURI().contains(EsUrl.SUFFIX_SEARCH)) {
                    String reqBody = getRequestBody(request);
                    logger.info("original curl -X POST -L '" + targetUrl + "' " + " --data '" + reqBody + "'");
                    StringBuilder sb = cacheService.executeSearch(targetUrl, reqBody);
                    if (sb.length() > 0) {
                        logger.info("sc ok ");
                        setZuulResponse(ctx, sb.toString());
                    } else {
                        logger.error("original request invoked.");
                    }
                } else if (request.getRequestURI().contains(EsUrl.SUFFIX_MULTI_GET)) {
                    if (enablePerformance) {
                        String reqBody = getRequestBody(request);
                        if (!reqBody.contains("\"_type\":\"config\"")) {
                            String resBody = performanceService.putDashboardCntAndReturnRes(reqBody);
                            setZuulResponse(ctx, resBody);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        if (request != null && request.getReader() != null && request.getReader().lines() != null) {
            return request.getReader().lines().collect(Collectors.joining(System.lineSeparator())) + "\n";
        }
        return null;
    }

    public String getTargetUrl(HttpServletRequest request) {
        String suffixUrl;
        if (!StringUtils.isEmpty(request.getQueryString())) {
            suffixUrl = request.getRequestURI() + "?" + request.getQueryString();
        } else {
            suffixUrl = request.getRequestURI();
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

    public boolean isSkipUrl(HttpServletRequest request) {
        if (request.getRequestURI().equals("/.kibana/config/_search") ||
                request.getRequestURI().equals("/_cluster/settings") ||
                request.getRequestURI().equals("/_nodes/_local") ||
                request.getRequestURI().equals("/_nodes") ||
                request.getRequestURI().equals("/") ||
                request.getRequestURI().equals("/_mapping") ||
                request.getRequestURI().equals("/_aliases") ||
                request.getRequestURI().equals("/_cluster/health/.kibana") ||
                request.getRequestURI().contains("\\.kibana")) {
            return true;
        }
        return false;
    }
}