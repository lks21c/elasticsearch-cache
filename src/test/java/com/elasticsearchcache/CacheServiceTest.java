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
        String resBody = "{\n" +
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
                "";
        System.out.println("resBody = " + resBody);
        String newRes = cacheService.generateTermsRes(resBody);

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

    @Test
    public void testGenerateTermsRes3() {
        StringBuilder sb = new StringBuilder();
//        String newRes = cacheService.generateTermsRes("{\n" +
//                "  \"took\": 28704,\n" +
//                "  \"timed_out\": false,\n" +
//                "  \"_shards\": {\n" +
//                "    \"total\": 8,\n" +
//                "    \"successful\": 8,\n" +
//                "    \"failed\": 0\n" +
//                "  },\n" +
//                "  \"hits\": {\n" +
//                "    \"total\": 5863317,\n" +
//                "    \"max_score\": 0,\n" +
//                "    \"hits\": []\n" +
//                "  },\n" +
//                "  \"aggregations\": {\n" +
//                "    \"NAME\": {\n" +
//                "      \"buckets\": [\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 83906\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60681\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22835\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46758\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 62317\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 81716\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72173\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53515\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20761\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38197\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53531\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 72039\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41011\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38857\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15064\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39468\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37528\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40752\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2016\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1914\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1238\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1909\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1922\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2015\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 886\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 755\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 722\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 784\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 883\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 512\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 402\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 511\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 293\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 263\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 248\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 267\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 291\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 218\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 167\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 218\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 145\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 118\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 82\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 82\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 142\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 113\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 97\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 93\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 90\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 100\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 61\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 61\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 55\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 33\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:38:00.000Z\",\n" +
//                "          \"key\": 1516009080000,\n" +
//                "          \"doc_count\": 199058\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 69964\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 51808\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20531\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40871\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52912\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 68364\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 60984\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 45424\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 18121\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 34143\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 45764\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 60852\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 34190\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 33028\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13420\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 33486\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 30647\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 33924\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1665\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1603\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1018\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1609\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1611\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1665\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 814\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 721\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 688\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 748\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 814\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 466\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 369\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 466\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 286\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 236\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 227\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 237\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 285\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 214\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 155\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 214\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 124\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 77\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 110\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 111\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 99\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 77\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 75\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 77\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 61\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 61\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 29\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 43\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 40\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 15\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG SMART TV (Linux)\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:39:00.000Z\",\n" +
//                "          \"key\": 1516009140000,\n" +
//                "          \"doc_count\": 167177\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 86105\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 62290\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23196\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47634\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 63170\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 83869\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72962\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53815\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20896\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38457\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53510\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 72822\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41658\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39533\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15342\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39566\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37544\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41360\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1970\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1878\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1183\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1880\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1886\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1970\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 881\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 781\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 724\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 809\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 881\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 547\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 397\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 542\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 276\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 250\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 235\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 254\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 276\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 201\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 159\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 200\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 154\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 154\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 143\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 116\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 142\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 113\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 87\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 103\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 112\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 93\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 106\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 103\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 106\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 45\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 37\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 20\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 20\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 17\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:40:00.000Z\",\n" +
//                "          \"key\": 1516009200000,\n" +
//                "          \"doc_count\": 202681\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 85018\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 61351\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23221\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47995\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 63163\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 82856\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 73646\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 54374\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21070\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39360\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 54292\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 73382\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41840\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39580\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15195\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39895\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37805\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41473\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1997\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1897\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1222\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1901\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1907\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1996\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1001\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 810\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 800\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 847\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 996\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 534\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 395\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 529\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 367\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 274\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 300\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 279\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 363\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 233\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 190\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 232\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 171\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 131\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 161\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 132\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 132\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 89\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 77\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 89\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 59\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 64\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 32\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 44\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 36\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 21\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 21\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 19\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 18\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 19\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:41:00.000Z\",\n" +
//                "          \"key\": 1516009260000,\n" +
//                "          \"doc_count\": 202533\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 86094\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 62001\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23516\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47311\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 62697\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 83769\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 73221\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53308\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21185\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38835\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53330\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 72937\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41876\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39507\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15338\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39980\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37376\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41499\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2134\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2011\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1299\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2015\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2020\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2133\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 903\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 785\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 745\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 816\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 903\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 558\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 417\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 558\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 299\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 260\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 237\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 264\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 296\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 199\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 169\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 199\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 143\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 117\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 90\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 124\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 120\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 115\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 118\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 88\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 116\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 113\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 113\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 63\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 45\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 32\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 45\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 18\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 18\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG SMART TV (Linux)\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:42:00.000Z\",\n" +
//                "          \"key\": 1516009320000,\n" +
//                "          \"doc_count\": 202979\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 86329\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 61743\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23301\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47218\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 62466\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 84142\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 71759\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53841\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20718\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38905\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 54118\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71634\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41539\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39777\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15403\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39942\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37371\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41215\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2026\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1916\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1238\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1921\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1926\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2026\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 961\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 819\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 794\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 849\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 957\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 551\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 372\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 549\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 340\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 267\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 259\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 274\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 340\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 233\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 191\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 233\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 138\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 133\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 129\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 107\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 124\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 115\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 95\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 90\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 87\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 87\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 95\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 80\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 62\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 43\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 16\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 16\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:43:00.000Z\",\n" +
//                "          \"key\": 1516009380000,\n" +
//                "          \"doc_count\": 201799\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 85086\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 61955\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23152\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47796\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 62553\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 82820\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72526\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53431\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21021\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39023\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 54349\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 72382\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41520\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39787\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14905\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39450\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37107\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41155\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2040\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1896\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1211\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1899\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1906\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2039\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 954\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 815\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 760\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 841\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 952\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 529\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 401\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 529\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 350\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 282\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 267\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 289\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 348\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 232\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 178\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 230\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 134\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 107\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 131\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 130\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 109\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 120\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 106\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 93\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 106\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 90\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 87\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 58\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 56\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 20\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 20\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 23\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 22\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 22\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 22\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 22\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:44:00.000Z\",\n" +
//                "          \"key\": 1516009440000,\n" +
//                "          \"doc_count\": 201093\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 84765\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60530\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23019\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47545\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61799\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 82650\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72443\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53964\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21180\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38707\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 54218\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 72279\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 42021\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39587\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15349\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40451\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37750\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41682\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1972\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1867\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1245\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1869\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1879\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1971\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 887\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 790\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 745\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 818\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 887\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 599\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 426\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 599\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 343\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 292\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 266\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 301\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 342\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 225\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 175\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 224\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 161\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 59\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 136\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 149\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 139\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 120\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 94\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 108\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 87\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 100\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 76\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 50\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 39\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 15\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:45:00.000Z\",\n" +
//                "          \"key\": 1516009500000,\n" +
//                "          \"doc_count\": 201353\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 85294\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 62065\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23298\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47319\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 62689\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 83135\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 71514\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52510\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20821\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38473\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53419\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71203\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41610\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39563\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15265\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39569\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37499\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41239\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1985\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1889\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1229\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1894\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1898\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1984\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 929\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 822\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 760\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 849\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 929\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 591\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 419\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 590\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 321\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 268\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 263\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 275\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 318\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 224\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 173\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 222\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 162\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 119\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 115\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 68\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 113\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 106\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 106\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 92\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 67\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 56\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 42\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 19\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 19\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG SMART TV (Linux)\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:46:00.000Z\",\n" +
//                "          \"key\": 1516009560000,\n" +
//                "          \"doc_count\": 200297\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 85083\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 61157\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23290\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47339\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61872\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 82973\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72075\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53510\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20811\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38645\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52740\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71792\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41593\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39599\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15277\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39526\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37637\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41228\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2059\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1948\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1269\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1946\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1956\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2058\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 959\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 833\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 780\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 860\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 958\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 567\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 410\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 566\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 315\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 265\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 258\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 271\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 314\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 228\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 184\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 226\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 171\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 82\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 128\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 161\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 151\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 68\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 131\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 118\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 103\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 109\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 109\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 118\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 94\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 69\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 58\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 46\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 30\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 17\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:47:00.000Z\",\n" +
//                "          \"key\": 1516009620000,\n" +
//                "          \"doc_count\": 200888\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 84621\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60812\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23149\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47759\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 62276\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 82293\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72523\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53347\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21180\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39032\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53413\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 72256\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41504\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39644\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14907\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39950\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37459\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41108\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2012\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1914\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1217\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1920\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1922\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2012\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 942\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 792\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 770\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 819\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 939\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 573\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 427\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 572\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 420\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 292\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 293\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 300\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 398\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 213\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 183\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 213\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 148\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 112\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 141\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 113\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 89\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 95\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 99\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 66\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 59\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 59\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 41\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 33\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 19\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 18\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 18\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 19\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:48:00.000Z\",\n" +
//                "          \"key\": 1516009680000,\n" +
//                "          \"doc_count\": 200379\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 84039\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60716\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23157\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47674\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61577\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 81934\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 71813\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53129\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20768\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38835\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53703\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71659\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41548\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39612\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15181\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40244\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37370\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41102\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1930\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1854\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1195\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1859\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1862\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1930\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 960\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 827\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 791\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 853\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 957\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 583\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 407\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 583\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 419\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 297\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 287\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 303\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 389\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 229\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 167\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 223\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 90\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 101\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 79\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 75\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 75\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 103\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 103\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 48\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 82\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 51\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 49\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 41\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 18\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 18\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG SMART TV (Linux)\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:49:00.000Z\",\n" +
//                "          \"key\": 1516009740000,\n" +
//                "          \"doc_count\": 199384\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 83052\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 61208\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 23155\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46941\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61134\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 81058\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 71417\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52518\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21115\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38246\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52359\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71198\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41695\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39481\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15358\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39790\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36970\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41309\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2007\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1912\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1224\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1913\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1920\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2007\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 968\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 833\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 786\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 855\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 967\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 584\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 417\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 579\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 316\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 256\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 253\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 261\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 315\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 213\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 175\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 212\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 141\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 116\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 122\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 103\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 120\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 85\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 94\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 88\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 89\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 89\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 94\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 70\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 47\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 47\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:50:00.000Z\",\n" +
//                "          \"key\": 1516009800000,\n" +
//                "          \"doc_count\": 198283\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 82627\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60277\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22650\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46806\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60577\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 80410\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 71986\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52875\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21126\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38512\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53267\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71728\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41383\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39205\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15138\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39841\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37555\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41044\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2073\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1934\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1260\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1935\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1942\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2073\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1038\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 865\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 840\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 887\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 1034\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 620\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 453\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 616\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 353\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 286\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 273\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 290\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 347\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 205\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 173\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 201\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 137\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 79\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 106\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 132\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 136\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 110\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 110\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 110\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 82\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 98\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 62\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 66\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 48\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 48\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 35\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:51:00.000Z\",\n" +
//                "          \"key\": 1516009860000,\n" +
//                "          \"doc_count\": 198108\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 83687\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60036\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22832\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47407\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61604\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 81637\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 71294\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52704\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20786\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38353\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52728\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 71088\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41344\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39601\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15273\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39457\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37278\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41065\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1971\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1868\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1233\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1866\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1875\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1971\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 951\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 829\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 793\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 860\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 951\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 636\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 463\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 633\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 325\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 289\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 263\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 295\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 324\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 233\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 174\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 229\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 144\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 118\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 75\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 75\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 143\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 106\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 90\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 94\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 75\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 90\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 59\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 65\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 48\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 48\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 39\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 16\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 15\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:52:00.000Z\",\n" +
//                "          \"key\": 1516009920000,\n" +
//                "          \"doc_count\": 198545\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 83555\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 59899\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22589\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46772\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59995\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 81504\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70341\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52585\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20168\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37862\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52114\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 70228\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41460\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39952\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14870\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40216\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37194\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41148\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2036\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1914\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1274\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1918\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1922\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2036\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 987\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 818\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 798\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 849\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 984\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 591\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 435\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 591\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 312\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 278\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 241\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 285\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 312\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 231\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 179\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 228\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 159\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 107\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 152\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 117\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 108\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 95\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 88\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 64\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 32\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 49\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 43\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 24\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 24\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:53:00.000Z\",\n" +
//                "          \"key\": 1516009980000,\n" +
//                "          \"doc_count\": 197704\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 82127\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60442\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22489\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47104\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61213\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 80117\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70495\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 51856\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20661\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38260\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52626\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 70353\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41175\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38938\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15225\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38877\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36710\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40829\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1983\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1894\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1209\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1894\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1899\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1983\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 929\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 791\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 778\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 821\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 926\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 570\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 412\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 565\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 326\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 276\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 262\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 281\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 323\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 259\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 204\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 257\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 112\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 89\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 102\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 104\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 98\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 62\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 83\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 56\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 58\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 27\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 47\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 40\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 27\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 16\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 27\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:54:00.000Z\",\n" +
//                "          \"key\": 1516010040000,\n" +
//                "          \"doc_count\": 195958\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 81912\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 59662\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22724\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47184\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60623\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 79892\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70428\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 52673\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20521\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38250\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52079\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 70044\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41544\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39821\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15275\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40037\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37067\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41168\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1957\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1875\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1201\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1870\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1881\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1957\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 940\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 812\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 769\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 843\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 938\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 552\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 394\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 551\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 338\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 283\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 259\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 288\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 332\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 271\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 201\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 266\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 168\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 109\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 127\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 110\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 110\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 168\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 120\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 116\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 109\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 87\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 79\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 97\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 82\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 82\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 54\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 54\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 40\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:55:00.000Z\",\n" +
//                "          \"key\": 1516010100000,\n" +
//                "          \"doc_count\": 195813\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 82823\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60400\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22829\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46814\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60855\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 80732\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70080\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 51600\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20607\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38072\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52447\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 69688\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41422\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39108\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15086\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39624\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37156\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 41101\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1960\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1888\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1196\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1887\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1895\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1960\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 942\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 830\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 780\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 856\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 940\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 560\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 393\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 560\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 331\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 281\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 246\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 286\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 324\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 237\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 180\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 237\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 171\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 143\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 171\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 141\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 132\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 89\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 85\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 76\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 67\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 46\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 39\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:56:00.000Z\",\n" +
//                "          \"key\": 1516010160000,\n" +
//                "          \"doc_count\": 196220\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 83119\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 59484\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22845\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46526\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60154\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 80870\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 69774\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 51563\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20223\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38032\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52269\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 69394\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40931\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39517\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14906\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39895\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37077\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40700\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1999\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1913\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1198\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1911\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1920\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1999\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 963\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 835\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 792\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 865\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 963\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 603\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 418\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 597\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 340\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 283\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 277\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 288\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 338\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 245\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 194\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 242\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 125\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 68\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 68\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 118\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 82\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 98\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 95\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 90\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 58\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 73\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 61\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 73\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 50\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 39\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 17\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:57:00.000Z\",\n" +
//                "          \"key\": 1516010220000,\n" +
//                "          \"doc_count\": 195702\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 81194\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58904\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22317\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46320\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60221\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 79166\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 69102\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 51543\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20275\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38064\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 52269\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 68992\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40906\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39110\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 15063\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39080\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36602\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40624\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2026\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1882\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1211\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1884\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1888\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2021\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 953\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 777\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 758\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 808\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 950\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 582\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 420\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 582\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 313\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 270\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 254\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 276\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 310\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 237\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 172\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 237\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 113\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 107\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 93\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 93\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 98\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 82\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 63\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 76\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 73\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 54\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 44\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:58:00.000Z\",\n" +
//                "          \"key\": 1516010280000,\n" +
//                "          \"doc_count\": 193468\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 76723\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 55980\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21393\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 44476\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 56941\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 74745\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 65756\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 48480\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19528\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 36414\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 49567\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 65584\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 38781\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36937\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14528\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37038\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 35141\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 38499\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1795\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1702\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1110\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1707\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1709\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1795\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 920\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 791\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 748\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 819\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 916\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 511\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 377\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 510\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 289\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 241\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 232\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 247\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 288\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 212\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 165\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 210\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 121\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 119\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 106\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 93\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 106\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 93\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 87\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 50\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 64\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 24\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 46\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 43\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 23\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T09:59:00.000Z\",\n" +
//                "          \"key\": 1516010340000,\n" +
//                "          \"doc_count\": 183086\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 80484\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58213\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22117\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46215\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 58711\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 78505\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68134\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50603\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20124\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37760\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50762\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 68033\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41148\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38841\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14887\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39842\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37086\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40911\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2039\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1874\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1220\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1869\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1880\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2035\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 924\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 816\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 766\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 845\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 924\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 539\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 381\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 537\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 330\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 299\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 253\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 304\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 328\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 234\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 178\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 232\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 141\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 117\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 140\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 112\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 112\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 104\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 95\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 48\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 70\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 61\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 64\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 51\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 40\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 23\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 16\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 23\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:00:00.000Z\",\n" +
//                "          \"key\": 1516010400000,\n" +
//                "          \"doc_count\": 192156\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 79988\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 59057\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21890\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46007\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59830\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 77997\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68250\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50555\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19823\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37952\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 51499\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 68130\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41129\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39480\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14810\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39195\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36468\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40705\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2130\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1987\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1326\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1987\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1994\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 2128\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 975\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 809\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 784\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 837\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 972\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 621\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 393\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 617\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 354\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 283\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 282\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 286\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 348\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 211\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 170\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 211\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 109\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 98\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 101\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 95\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 79\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 48\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 79\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 48\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 62\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 62\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 65\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 25\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 33\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 40\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 35\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:01:00.000Z\",\n" +
//                "          \"key\": 1516010460000,\n" +
//                "          \"doc_count\": 191680\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 80373\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58978\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22091\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46610\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 60077\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 78399\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68497\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50578\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20199\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37450\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50831\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 68367\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40908\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39026\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14855\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39095\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36705\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40531\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1967\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1880\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1193\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1882\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1886\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1966\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 943\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 832\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 765\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 860\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 942\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 574\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 419\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 573\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 376\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 269\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 295\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 274\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 371\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 219\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 160\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 213\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 110\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 110\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 96\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 92\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 71\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 65\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 65\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 49\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 36\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 15\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 15\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:02:00.000Z\",\n" +
//                "          \"key\": 1516010520000,\n" +
//                "          \"doc_count\": 191937\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 80584\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58350\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22193\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46097\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59225\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 78542\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68591\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 51124\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19846\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37405\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 51192\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 68472\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40759\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38850\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14905\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39345\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36743\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40441\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1939\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1858\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1164\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1860\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1865\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1939\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 936\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 798\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 781\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 826\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 932\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 600\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 417\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 600\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 331\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 272\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 262\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 277\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 331\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 250\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 191\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 247\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 138\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 110\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 135\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 111\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 102\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 110\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 105\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 80\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 95\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 59\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 65\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 55\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 55\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 45\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 20\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 40\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 41\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 41\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 8\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 3\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPad PC Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:03:00.000Z\",\n" +
//                "          \"key\": 1516010580000,\n" +
//                "          \"doc_count\": 192126\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 80041\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58832\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21844\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46142\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59616\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 78007\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68147\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50701\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19873\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37291\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50851\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 68010\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 41026\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38957\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14665\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39439\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36260\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40738\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1923\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1842\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1179\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1833\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1849\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1923\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 935\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 835\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 759\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 864\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 934\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 634\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 434\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 631\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 311\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 261\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 247\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 266\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 310\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 229\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 192\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 228\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 134\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 123\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 111\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 124\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 124\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 132\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 112\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 81\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 77\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 96\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 71\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 61\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 61\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 94\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 49\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 70\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 54\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 68\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 37\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 37\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 34\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 24\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 34\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 16\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 16\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 11\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 11\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:04:00.000Z\",\n" +
//                "          \"key\": 1516010640000,\n" +
//                "          \"doc_count\": 191405\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 80042\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58776\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22335\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46253\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59751\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 78148\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 67607\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50431\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19858\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37423\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50148\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 67475\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40742\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38779\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14900\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39422\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36586\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40441\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1998\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1903\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1224\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1906\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1908\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1994\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 909\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 787\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 721\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 814\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 909\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 621\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 447\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 620\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 324\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 287\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 264\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 290\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 323\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 230\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 191\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 228\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 133\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 70\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 107\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 72\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 128\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 130\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 88\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 120\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 99\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 92\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 101\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 108\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 46\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 69\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 64\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 78\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 66\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 51\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 66\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 44\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 38\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 39\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 17\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 8\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 14\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 13\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:05:00.000Z\",\n" +
//                "          \"key\": 1516010700000,\n" +
//                "          \"doc_count\": 190782\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 79121\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 58790\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 21851\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 46603\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59743\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 77278\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 67565\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50602\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19805\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37428\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 50658\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 67424\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40973\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 39206\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14875\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 39381\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36965\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40611\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1970\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1863\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1245\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1867\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1869\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1970\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 968\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 820\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 786\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 856\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 968\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 572\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 406\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 571\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 331\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 264\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 267\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 268\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 326\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 256\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 200\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 252\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 115\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 83\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 79\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 79\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 114\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 108\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 78\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 101\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 87\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 91\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 13\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 57\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 74\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 60\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 35\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 53\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 52\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 60\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 45\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 47\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 30\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 36\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 34\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 39\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 7\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 7\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 4\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Macintosh PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:06:00.000Z\",\n" +
//                "          \"key\": 1516010760000,\n" +
//                "          \"doc_count\": 189969\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"8\": {\n" +
//                "            \"doc_count_error_upper_bound\": 0,\n" +
//                "            \"sum_other_doc_count\": 0,\n" +
//                "            \"buckets\": [\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 79863\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 57451\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 22169\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 45665\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 59119\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone App\",\n" +
//                "                \"doc_count\": 78000\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 68031\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 50769\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 19858\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 37125\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 51438\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone App\",\n" +
//                "                \"doc_count\": 67838\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 40520\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 38291\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 14665\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 38848\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 36196\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player\",\n" +
//                "                \"doc_count\": 40248\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1981\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1848\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1179\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1847\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1854\n" +
//                "                },\n" +
//                "                \"key\": \"iPad App\",\n" +
//                "                \"doc_count\": 1981\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 929\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 808\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 741\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 842\n" +
//                "                },\n" +
//                "                \"key\": \"카카오 미니 스피커\",\n" +
//                "                \"doc_count\": 929\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 571\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 399\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Melon with kakao\",\n" +
//                "                \"doc_count\": 571\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 333\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 279\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 262\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 283\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Win8 Tablet App\",\n" +
//                "                \"doc_count\": 327\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 268\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 221\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 3\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Melon with kakao\",\n" +
//                "                \"doc_count\": 268\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 121\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 74\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 91\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 76\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2014 (르노삼성 SM6,QM6)\",\n" +
//                "                \"doc_count\": 116\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 103\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 96\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 98\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 97\n" +
//                "                },\n" +
//                "                \"key\": \"Android Tablet App\",\n" +
//                "                \"doc_count\": 103\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 100\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 85\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 84\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"구분불가\",\n" +
//                "                \"doc_count\": 97\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 86\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 44\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 73\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 59\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG 냉장고 패밀리허브\",\n" +
//                "                \"doc_count\": 82\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 67\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 47\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 43\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 40\n" +
//                "                },\n" +
//                "                \"key\": \"PC Web\",\n" +
//                "                \"doc_count\": 67\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 39\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 42\n" +
//                "                },\n" +
//                "                \"key\": \"SKT NUGU 스피커\",\n" +
//                "                \"doc_count\": 42\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 31\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 24\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 29\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 27\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 27\n" +
//                "                },\n" +
//                "                \"key\": \"SKT T2C(르노삼성 QM3) IN-DASH\",\n" +
//                "                \"doc_count\": 31\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 12\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"BNTV-SMART Settop\",\n" +
//                "                \"doc_count\": 12\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 10\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S2(S3)\",\n" +
//                "                \"doc_count\": 10\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 9\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SKT MM2012 (르노삼성 SM3,5,7,QM5)\",\n" +
//                "                \"doc_count\": 9\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 6\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 4\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"Android Phone Web\",\n" +
//                "                \"doc_count\": 6\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"key\": \"PC Player HomeTab\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 5\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG MULTIROOM\",\n" +
//                "                \"doc_count\": 5\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"SKT 루나 WATCH\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 2\n" +
//                "                },\n" +
//                "                \"key\": \"iPhone Web\",\n" +
//                "                \"doc_count\": 2\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 0\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"Linux PC Web\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"SAMSUNG GEAR S\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              },\n" +
//                "              {\n" +
//                "                \"1\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"4\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"5\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"6\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"7\": {\n" +
//                "                  \"value\": 1\n" +
//                "                },\n" +
//                "                \"key\": \"미러링크 현대차OEM\",\n" +
//                "                \"doc_count\": 1\n" +
//                "              }\n" +
//                "            ]\n" +
//                "          },\n" +
//                "          \"key_as_string\": \"2018-01-15T10:07:00.000Z\",\n" +
//                "          \"key\": 1516010820000,\n" +
//                "          \"doc_count\": 190754\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}");
//
//        System.out.println("newRes = " + newRes);
    }
}
