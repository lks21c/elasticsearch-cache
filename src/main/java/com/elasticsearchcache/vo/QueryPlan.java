package com.elasticsearchcache.vo;

import org.apache.commons.lang.StringUtils;

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

    public int getTotalQueryCnt() {
        int queryCnt = 0;
        if (!StringUtils.isEmpty(preQuery)) {
            queryCnt++;
        }
        if (!StringUtils.isEmpty(query)) {
            queryCnt++;
        }
        if (!StringUtils.isEmpty(postQuery)) {
            queryCnt++;
        }
        return queryCnt;
    }
}
