package com.elasticsearchcache.util;

public class UrlUtil {

    public static String getQueryString(String targetUrl) {
        String queryString = "";
        if (targetUrl.contains("?")) {
            queryString = targetUrl.substring(targetUrl.indexOf("?"), targetUrl.length());
        }
        return queryString;
    }
}
