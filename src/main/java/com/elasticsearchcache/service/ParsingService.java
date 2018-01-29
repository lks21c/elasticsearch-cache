package com.elasticsearchcache.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregatorFactories;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;

public interface ParsingService {

    Map<String, Object> parseXContent(String str);

    QueryBuilder parseQuery(String query) throws IOException;

    AggregatorFactories.Builder parseAggs(String aggs) throws IOException;


    Map<String, Object> getQueryWithoutRange(Map<String, Object> query);

    Map<String,Object> parseStartEndDt(Map<String, Object> query);
}
