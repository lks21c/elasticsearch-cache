package com.elasticsearchcache.vo;

import java.io.Serializable;
import java.util.List;

public class QueryPlan implements Serializable {
    private String preQuery;

    private String query;

    private String postQuery;

    private List<DateHistogramBucket> dhbList;

    private CachePlan cachePlan;

    private String interval;

    private String indexName;

    private long indexSize;

    private String queryWithoutRange;

    private String aggs;

    private String aggsType;

    private String aggsKey;

    private boolean isMultiSearch;

    private String queryString;

    private List<Integer> termsSizeList;

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

    public CachePlan getCachePlan() {
        return cachePlan;
    }

    public void setCachePlan(CachePlan cachePlan) {
        this.cachePlan = cachePlan;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public long getIndexSize() {
        return indexSize;
    }

    public void setIndexSize(long indexSize) {
        this.indexSize = indexSize;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getQueryWithoutRange() {
        return queryWithoutRange;
    }

    public void setQueryWithoutRange(String queryWithoutRange) {
        this.queryWithoutRange = queryWithoutRange;
    }

    public String getAggs() {
        return aggs;
    }

    public void setAggs(String aggs) {
        this.aggs = aggs;
    }

    public String getAggsType() {
        return aggsType;
    }

    public void setAggsType(String aggsType) {
        this.aggsType = aggsType;
    }

    public String getAggsKey() {
        return aggsKey;
    }

    public void setAggsKey(String aggsKey) {
        this.aggsKey = aggsKey;
    }

    public boolean isMultiSearch() {
        return isMultiSearch;
    }

    public void setMultiSearch(boolean multiSearch) {
        isMultiSearch = multiSearch;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<Integer> getTermsSizeList() {
        return termsSizeList;
    }

    public void setTermsSizeList(List<Integer> termsSizeList) {
        this.termsSizeList = termsSizeList;
    }
}
