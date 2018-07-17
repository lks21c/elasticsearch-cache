package com.elasticsearchcache;

import com.elasticsearchcache.service.ParsingService;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

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
}
