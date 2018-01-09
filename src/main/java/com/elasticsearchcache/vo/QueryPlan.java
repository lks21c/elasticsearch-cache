package com.elasticsearchcache.vo;

import java.util.List;

public class QueryPlan {
    private String preQuery;

    private String query;

    private String postQuery;

    private List<DateHistogramBucket> dhbList;

    public String getPreQuery() {
        return preQuery;
    }

    public void setPreQuery(String preQuery) {
        this.preQuery = preQuery;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPostQuery() {
        return postQuery;
    }

    public void setPostQuery(String postQuery) {
        this.postQuery = postQuery;
    }

    public List<DateHistogramBucket> getDhbList() {
        return dhbList;
    }

    public void setDhbList(List<DateHistogramBucket> dhbList) {
        this.dhbList = dhbList;
    }
}
