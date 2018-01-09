package com.elasticsearchcache.repository;

import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.JsonUtil;
import com.elasticsearchcache.vo.DateHistogramBucket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.hash.MurmurHash3;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lks21c
 */
@Service("EsCacheRepositoryImpl")
public class EsCacheRepositoryImpl implements CacheRepository {
    private static final Logger logger = LogManager.getLogger(EsCacheRepositoryImpl.class);

    @Autowired
    private RestHighLevelClient restClient;

    @Autowired
    private ParsingService parsingService;

    @Override
    public List<DateHistogramBucket> getCache(String indexName, String query, String agg, DateTime startDt, DateTime endDt) throws IOException {
        String key = indexName + query + agg;
        logger.info("get cache " + key);

        List<QueryBuilder> qbList = new ArrayList<>();
        qbList.add(QueryBuilders.termQuery("key", key));
        qbList.add(QueryBuilders.rangeQuery("ts").from(startDt).to(endDt));

        BoolQueryBuilder bq = QueryBuilders.boolQuery();
        bq.must().addAll(qbList);

        SearchSourceBuilder sb = new SearchSourceBuilder();
        sb.query(bq);
        sb.size(10000);
        sb.sort("ts", SortOrder.ASC);

        SearchRequest srch = new SearchRequest().indices("cache").types("info");
        srch.source(sb);

        logger.info("srch = " + sb.toString());
        SearchResponse sr = restClient.search(srch);
        logger.info("sr = " + sr.toString());

        List<DateHistogramBucket> dhbList = new ArrayList<>();
        for (SearchHit hit : sr.getHits().getHits()) {
            Map<String, Object> source = hit.getSourceAsMap();
            Long ts = (Long) source.get("ts");
            String value = (String) source.get("value");
            Map<String, Object> bucket = new Gson().fromJson(value, HashMap.class);
            dhbList.add(new DateHistogramBucket(new DateTime(ts), bucket));
        }

        return dhbList;
    }

    @Override
    public void putCache(String indexName, String query, String agg, List<DateHistogramBucket> dhbList) throws JsonProcessingException {
        String key = indexName + query + agg;
        BulkRequest br = new BulkRequest();
        for (DateHistogramBucket dhb : dhbList) {
            Map<String, Object> bucket = dhb.getBucket();
            String key_as_string = (String) bucket.get("key_as_string");
            logger.info("for key_as_string = " + key_as_string);
            Long ts = (Long) bucket.get("key");
            String str = key + "_" + ts;
            MurmurHash3.Hash128 hash = MurmurHash3.hash128(str.getBytes(), 0, str.getBytes().length, 0, new MurmurHash3.Hash128());
            String id = String.valueOf(hash.h1) + String.valueOf(hash.h2);
            IndexRequest ir = new IndexRequest("cache", "info", id);
            Map<String, Object> irMap = new HashMap<>();
            irMap.put("value", JsonUtil.convertAsString(bucket));
            irMap.put("key", key);
            irMap.put("ts", ts);
            ir.source(irMap);
            br.add(ir);
        }

        restClient.bulkAsync(br, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

    }
}
