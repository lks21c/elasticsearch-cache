package com.elasticsearchcache.repository;

import com.elasticsearchcache.vo.DateHistogramBucket;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

/**
 * @author lks21c
 */
public interface CacheRepository {
    public List<DateHistogramBucket> getCache(String indexName, String query, String agg, DateTime startDt, DateTime endDt) throws IOException;

    public void putCache(String indexName, String query, String agg, List<DateHistogramBucket> dhbList) throws IOException;
}
