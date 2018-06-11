package com.elasticsearchcache.util;

public class KeyGenerator {
    public static String generateProfileKey(String indexName, String queryString, String iMapStr, String qMapStr) {
        return indexName + "\n" + queryString + "\n" + iMapStr + "\n" + qMapStr;
    }

    public static String generateCacheKey(String indexName, String queryString, long indexSize, String query, String agg) {
        return indexName + queryString + indexSize + query + agg;
    }
}
