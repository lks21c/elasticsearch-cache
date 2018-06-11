package com.elasticsearchcache.repository;

import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.DateHistogramBucket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO: This implementation needs to be completed.
 *
 * @author lks21c
 */
@Service("InMemoryCacheRepositoryImpl")
public class InMemoryCacheRepositoryImpl implements CacheRepository {
    private Logger logger = LogManager.getLogger(InMemoryCacheRepositoryImpl.class);

    public static final String CACHE_KEY = "cache";

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private ParsingService parsingService;

    public Map<String, Object> getCache(String key, int year, int month, Integer day, int hour, int minute, String query) {
        String cacheKey = key + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + query;
        //        logger.info("get key = " + key);
        Map<String, Object> cachePeriod = (Map<String, Object>) cacheManager.getCache(CACHE_KEY).get(key);

//        logger.info("cache : " + JsonUtil.convertAsString(cachePeriod));
        return cachePeriod;
    }

    @Override
    public List<DateHistogramBucket> getCache(String indexName, String queryString, long indexSize, String query, String agg, DateTime startDt, DateTime endDt) throws IOException {
        String key = indexName + agg;
        logger.info("get cache " + key);

        List<DateHistogramBucket> dhbList = new ArrayList<>();

        return dhbList;
    }

    @Override
    public void putCache(String indexName, String queryString, long indexSize, String query, String agg, List<DateHistogramBucket> dhbList) throws IOException {
        logger.info("cache list = " + JsonUtil.convertAsString(dhbList));
    }
}
