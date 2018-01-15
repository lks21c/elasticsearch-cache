package com.elasticsearchcache;

import com.elasticsearchcache.service.CacheService;
import com.elasticsearchcache.service.NativeParsingServiceImpl;
import org.apache.http.MethodNotSupportedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest {
    @Autowired
    NativeParsingServiceImpl parsingService;

    @Autowired
    CacheService cacheService;

    @Test
    public void test() throws IOException, MethodNotSupportedException {
        String i = "{\"index\":[\"mel_com_private_music_st_realtime_member_*\"],\"ignore_unavailable\":true,\"preference\":1513646426988}\n";
        String q = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true}},{\"query_string\":{\"analyze_wildcard\":true,\"query\":\"*\"}},{\"range\":{\"ts\":{\"gte\":1513263600000,\"lte\":1513752110170,\"format\":\"epoch_millis\"}}}],\"must_not\":[]}},\"size\":0,\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"ts\",\"interval\":\"1d\",\"time_zone\":\"Asia/Tokyo\",\"min_doc_count\":1},\"aggs\":{\"3\":{\"terms\":{\"field\":\"log_type\",\"size\":10,\"order\":{\"1\":\"desc\"}},\"aggs\":{\"1\":{\"sum\":{\"field\":\"datapoint\"}}}}}}},\"version\":true,\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{\"highlight_query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true,\"all_fields\":true}},{\"query_string\":{\"analyze_wildcard\":true,\"query\":\"*\",\"all_fields\":true}},{\"range\":{\"ts\":{\"gte\":1513263600000,\"lte\":1513752110170,\"format\":\"epoch_millis\"}}}],\"must_not\":[]}}}},\"fragment_size\":2147483647}}\n";
//        System.out.println(q);
        cacheService.manipulateQuery(i + q);
    }

    @Test
    public void test2() throws IOException, MethodNotSupportedException {
        String info = "{\"index\":[\"mel_com_private_app_event_realtime_20171220\",\"mel_com_private_app_event_realtime_20171221\",\"mel_com_private_app_event_realtime_20171217\",\"mel_com_private_app_event_realtime_20171219\",\"mel_com_private_app_event_realtime_20171218\"],\"ignore_unavailable\":true,\"preference\":1513927712118}\n" +
                "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true}},{\"query_string\":{\"analyze_wildcard\":true,\"query\":\"*\"}},{\"range\":{\"ts\":{\"gte\":1513436400000,\"lte\":1513846380354,\"format\":\"epoch_millis\"}}}],\"must_not\":[]}},\"size\":0,\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"ts\",\"interval\":\"1d\",\"time_zone\":\"Asia/Tokyo\",\"min_doc_count\":1},\"aggs\":{\"3\":{\"terms\":{\"field\":\"log_type\",\"size\":10,\"order\":{\"1\":\"desc\"}},\"aggs\":{\"1\":{\"sum\":{\"field\":\"datapoint\"}}}}}}},\"version\":true,\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{\"highlight_query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true,\"all_fields\":true}},{\"query_string\":{\"analyze_wildcard\":true,\"query\":\"*\",\"all_fields\":true}},{\"range\":{\"ts\":{\"gte\":1513436400000,\"lte\":1513846380354,\"format\":\"epoch_millis\"}}}],\"must_not\":[]}}}},\"fragment_size\":2147483647}}\n";
//        String body = cacheService.manipulateQuery(info);
//        System.out.println("final body = " + body);
    }

    @Test
    public void testGenerateTermsRes() {
        String newRes = cacheService.generateTermsRes("   {\n" +
                "      \"took\": 3,\n" +
                "      \"timed_out\": false,\n" +
                "      \"_shards\": {\n" +
                "        \"total\": 55,\n" +
                "        \"successful\": 55,\n" +
                "        \"failed\": 0\n" +
                "      },\n" +
                "      \"hits\": {\n" +
                "        \"total\": 2718971,\n" +
                "        \"max_score\": 0,\n" +
                "        \"hits\": []\n" +
                "      },\n" +
                "      \"aggregations\": {\n" +
                "        \"2\": {\n" +
                "          \"buckets\": \n" +
                "[{\"key_as_string\":\"2018-01-12T00:00:00.000Z\",\"doc_count\":1.7340322E7,\"3\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"Android Phone App\",\"doc_count\":9663386.0},{\"key\":\"iPhone App\",\"doc_count\":7331569.0},{\"key\":\"iPad App\",\"doc_count\":177331.0},{\"key\":\"Macintosh PC Player\",\"doc_count\":47057.0},{\"key\":\"Android Phone Melon with kakao\",\"doc_count\":39354.0},{\"key\":\"iPhone Web\",\"doc_count\":29318.0},{\"key\":\"iPhone Melon with kakao\",\"doc_count\":27332.0},{\"key\":\"Android Phone Web\",\"doc_count\":24579.0},{\"key\":\"미러링크 현대차OEM\",\"doc_count\":279.0},{\"key\":\"Android AZTalk Phone Web\",\"doc_count\":57.0},{\"key\":\"iPhone AZTalk Web\",\"doc_count\":42.0},{\"key\":\"Windows PC AZTalk Web\",\"doc_count\":7.0},{\"key\":\"Mobile AZTalk Web (Etc)\",\"doc_count\":3.0},{\"key\":\"PC AZTalk Web (Etc)\",\"doc_count\":2.0}]},\"key\":1.5157152E12},{\"key_as_string\":\"2018-01-13T00:00:00.000Z\",\"doc_count\":1.721735E7,\"3\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"Android Phone App\",\"doc_count\":9599165.0},{\"key\":\"iPhone App\",\"doc_count\":7267922.0},{\"key\":\"iPad App\",\"doc_count\":176713.0},{\"key\":\"Macintosh PC Player\",\"doc_count\":48385.0},{\"key\":\"Android Phone Melon with kakao\",\"doc_count\":41142.0},{\"key\":\"iPhone Web\",\"doc_count\":29795.0},{\"key\":\"iPhone Melon with kakao\",\"doc_count\":28380.0},{\"key\":\"Android Phone Web\",\"doc_count\":25558.0},{\"key\":\"미러링크 현대차OEM\",\"doc_count\":208.0},{\"key\":\"Android AZTalk Phone Web\",\"doc_count\":44.0},{\"key\":\"iPhone AZTalk Web\",\"doc_count\":26.0},{\"key\":\"Windows PC AZTalk Web\",\"doc_count\":4.0},{\"key\":\"Mobile AZTalk Web (Etc)\",\"doc_count\":2.0},{\"key\":\"PC AZTalk Web (Etc)\",\"doc_count\":1.0}]},\"key\":1.5158016E12}]            \n" +
                "        }\n" +
                "      },\n" +
                "      \"status\": 200\n" +
                "    }\n" +
                "");

        System.out.println("newRes = " + newRes);
    }

    @Test
    public void testGenerateTermsRes2() {
        String newRes = cacheService.generateTermsRes("{\n" +
                "      \"took\": 3,\n" +
                "      \"timed_out\": false,\n" +
                "      \"_shards\": {\n" +
                "        \"total\": 55,\n" +
                "        \"successful\": 55,\n" +
                "        \"failed\": 0\n" +
                "      },\n" +
                "      \"hits\": {\n" +
                "        \"total\": 2718971,\n" +
                "        \"max_score\": 0,\n" +
                "        \"hits\": []\n" +
                "      },\n" +
                "      \"aggregations\": {\n" +
                "        \"2\": {\n" +
                "          \"buckets\": \n" +
                "[{\"key_as_string\":\"2018-01-12T00:00:00.000Z\",\"doc_count\":1.7340322E7,\"3\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"3년전\",\"doc_count\":6452472.0,\"1\":{\"value\":4.7435609E7},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"00\",\"doc_count\":6452472.0,\"1\":{\"value\":4.7435609E7}}]}},{\"key\":\"2017\",\"doc_count\":5137319.0,\"1\":{\"value\":1.6193699E7},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":2716554.0,\"buckets\":[{\"key\":\"11\",\"doc_count\":508357.0,\"1\":{\"value\":1845081.0}},{\"key\":\"12\",\"doc_count\":501334.0,\"1\":{\"value\":1783883.0}},{\"key\":\"10\",\"doc_count\":440900.0,\"1\":{\"value\":1537100.0}},{\"key\":\"8\",\"doc_count\":489986.0,\"1\":{\"value\":1519766.0}},{\"key\":\"9\",\"doc_count\":480188.0,\"1\":{\"value\":1484035.0}}]}},{\"key\":\"2016\",\"doc_count\":3016342.0,\"1\":{\"value\":8676979.0},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":1356518.0,\"buckets\":[{\"key\":\"10\",\"doc_count\":419525.0,\"1\":{\"value\":1193923.0}},{\"key\":\"11\",\"doc_count\":370212.0,\"1\":{\"value\":1037045.0}},{\"key\":\"12\",\"doc_count\":359072.0,\"1\":{\"value\":1001625.0}},{\"key\":\"9\",\"doc_count\":297291.0,\"1\":{\"value\":825231.0}},{\"key\":\"5\",\"doc_count\":213724.0,\"1\":{\"value\":624597.0}}]}},{\"key\":\"2015\",\"doc_count\":2229852.0,\"1\":{\"value\":6546069.0},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":1209057.0,\"buckets\":[{\"key\":\"10\",\"doc_count\":226563.0,\"1\":{\"value\":684667.0}},{\"key\":\"11\",\"doc_count\":218653.0,\"1\":{\"value\":657157.0}},{\"key\":\"12\",\"doc_count\":197578.0,\"1\":{\"value\":586494.0}},{\"key\":\"9\",\"doc_count\":187274.0,\"1\":{\"value\":566827.0}},{\"key\":\"8\",\"doc_count\":190727.0,\"1\":{\"value\":563428.0}}]}},{\"key\":\"2018\",\"doc_count\":393202.0,\"1\":{\"value\":1511136.0},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"1\",\"doc_count\":393202.0,\"1\":{\"value\":1511136.0}}]}}]},\"key\":1.5157152E12},{\"key_as_string\":\"2018-01-13T00:00:00.000Z\",\"doc_count\":1.721735E7,\"3\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"3년전\",\"doc_count\":6403271.0,\"1\":{\"value\":4.6026538E7},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"00\",\"doc_count\":6403271.0,\"1\":{\"value\":4.6026538E7}}]}},{\"key\":\"2017\",\"doc_count\":5090765.0,\"1\":{\"value\":1.5847098E7},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":2712963.0,\"buckets\":[{\"key\":\"12\",\"doc_count\":494717.0,\"1\":{\"value\":1741035.0}},{\"key\":\"11\",\"doc_count\":493567.0,\"1\":{\"value\":1727363.0}},{\"key\":\"10\",\"doc_count\":434162.0,\"1\":{\"value\":1498411.0}},{\"key\":\"8\",\"doc_count\":482365.0,\"1\":{\"value\":1474426.0}},{\"key\":\"9\",\"doc_count\":472991.0,\"1\":{\"value\":1466195.0}}]}},{\"key\":\"2016\",\"doc_count\":2996003.0,\"1\":{\"value\":8499341.0},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":1342056.0,\"buckets\":[{\"key\":\"10\",\"doc_count\":417543.0,\"1\":{\"value\":1158344.0}},{\"key\":\"12\",\"doc_count\":357544.0,\"1\":{\"value\":1000509.0}},{\"key\":\"11\",\"doc_count\":365825.0,\"1\":{\"value\":1000342.0}},{\"key\":\"9\",\"doc_count\":297257.0,\"1\":{\"value\":816436.0}},{\"key\":\"5\",\"doc_count\":215778.0,\"1\":{\"value\":625658.0}}]}},{\"key\":\"2015\",\"doc_count\":2205324.0,\"1\":{\"value\":6410332.0},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":1190933.0,\"buckets\":[{\"key\":\"10\",\"doc_count\":226672.0,\"1\":{\"value\":683167.0}},{\"key\":\"11\",\"doc_count\":218029.0,\"1\":{\"value\":645222.0}},{\"key\":\"12\",\"doc_count\":196125.0,\"1\":{\"value\":571099.0}},{\"key\":\"9\",\"doc_count\":185945.0,\"1\":{\"value\":560427.0}},{\"key\":\"8\",\"doc_count\":187620.0,\"1\":{\"value\":555525.0}}]}},{\"key\":\"2018\",\"doc_count\":409460.0,\"1\":{\"value\":1562537.0},\"4\":{\"doc_count_error_upper_bound\":0.0,\"sum_other_doc_count\":0.0,\"buckets\":[{\"key\":\"1\",\"doc_count\":409460.0,\"1\":{\"value\":1562537.0}}]}}]},\"key\":1.5158016E12}]            \n" +
                "        }\n" +
                "      },\n" +
                "      \"status\": 200\n" +
                "    }");

        System.out.println("newRes = " + newRes);
    }
}
