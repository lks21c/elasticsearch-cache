package com.elasticsearchcache;

import com.elasticsearchcache.service.ParsingService;
import com.elasticsearchcache.util.JsonUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParsingServiceTest {

    @Autowired
    ParsingService parsingService;

    @Test
    public void name() throws IOException {
        String q = "{\n" +
                "    \"bool\" : {\n" +
                "      \"must\" : [\n" +
                "        {\n" +
                "          \"query_string\" : {\n" +
                "            \"query\" : \"_exists_:poc_nm AND ( poc_code:AS40 OR poc_code:IS40 )\",\n" +
                "            \"fields\" : [ ],\n" +
                "            \"use_dis_max\" : true,\n" +
                "            \"tie_breaker\" : 0.0,\n" +
                "            \"default_operator\" : \"or\",\n" +
                "            \"auto_generate_phrase_queries\" : false,\n" +
                "            \"max_determinized_states\" : 10000,\n" +
                "            \"enable_position_increments\" : true,\n" +
                "            \"fuzziness\" : \"AUTO\",\n" +
                "            \"fuzzy_prefix_length\" : 0,\n" +
                "            \"fuzzy_max_expansions\" : 50,\n" +
                "            \"phrase_slop\" : 0,\n" +
                "            \"escape\" : false,\n" +
                "            \"split_on_whitespace\" : true,\n" +
                "            \"boost\" : 1.0\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"range\" : {\n" +
                "            \"ts\" : {\n" +
                "              \"from\" : \"2017-01-01T00:00:00\",\n" +
                "              \"to\" : \"2018-01-01T00:00:00\",\n" +
                "              \"include_lower\" : true,\n" +
                "              \"include_upper\" : false,\n" +
                "              \"time_zone\" : \"+09:00\",\n" +
                "              \"boost\" : 1.0\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"disable_coord\" : false,\n" +
                "      \"adjust_pure_negative\" : true,\n" +
                "      \"boost\" : 1.0\n" +
                "    }\n" +
                "  }";

        QueryBuilder qb = parsingService.parseQuery(q);

        System.out.println("name = " + qb.getName());
    }

    @Test
    public void name2() throws IOException {
        String query = "{\"bool\":{\"must\":[{\"range\":{\"ts\":{\"from\":\"2018-07-17T00:00:00.000\",\"to\":\"2018-07-23T23:59:59.999\",\"include_lower\":true,\"include_upper\":true,\"time_zone\":\"+09:00\",\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}";

        Map<String, Object> qMap = parsingService.parseXContent(query);

        Map<String, Object> map = parsingService.parseStartEndDt(qMap);
        System.out.println(JsonUtil.convertAsString(map));
    }

    @Test
    public void name3() throws IOException {
        String query = "{\"bool\":{\"must\":[{\"range\":{\"ts\":{\"gte\":\"2018-07-17T00:00:00.000\",\"lte\":\"2018-07-23T23:59:59.999\",\"include_lower\":true,\"include_upper\":true,\"time_zone\":\"+09:00\",\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}";
        System.out.println(query);
        Map<String, Object> qMap = parsingService.parseXContent(query);

        Map<String, Object> map = parsingService.parseStartEndDt(qMap);
        System.out.println(JsonUtil.convertAsString(map));
    }

    @Test
    public void name4() throws IOException {
        String query = "{\"bool\":{\"must\":[{\"range\":{\"ts\":{\"gte\":\"2018-07-17T00:00:00.000\",\"lte\":\"2018-07-23T23:59:59.999\",\"time_zone\":\"+09:00\",\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}";
        System.out.println(query);
        Map<String, Object> qMap = parsingService.parseXContent(query);

        Map<String, Object> map = parsingService.parseStartEndDt(qMap);
        System.out.println(JsonUtil.convertAsString(map));
    }

    @Test
    public void name5() throws IOException {
        String q = "{\"bool\":{\"must\":[{\"range\":{\"ts\":{\"from\":\"2018-07-17T00:00:00.000\",\"to\":\"2018-07-23T23:59:59.999\",\"include_lower\":true,\"include_upper\":true,\"time_zone\":\"+09:00\",\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}";
        Map<String, Object> qMap = parsingService.parseXContent(q);
        Map<String, Object> map = parsingService.getQueryWithoutRange(qMap);
        System.out.println(JsonUtil.convertAsString(map));
    }

    @Test
    public void name7() throws IOException {
        String q = "{\"time\":{\"date_histogram\":{\"field\":\"ts\",\"time_zone\":\"+09:00\",\"interval\":\"1h\",\"offset\":0,\"order\":{\"_key\":\"asc\"},\"keyed\":false,\"min_doc_count\":0},\"aggregations\":{\"datapoint\":{\"sum\":{\"field\":\"datapoint\"}}}}}";
        Map<String, Object> aggs = parsingService.parseXContent(q);
        parsingService.parseIntervalAndAggsType(aggs, "1d");
    }
}
