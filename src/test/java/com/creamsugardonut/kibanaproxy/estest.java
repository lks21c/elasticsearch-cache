package com.creamsugardonut.kibanaproxy;

import com.creamsugardonut.kibanaproxy.service.ElasticSearchService;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class estest {
    @Autowired
    private ElasticSearchService esService;

    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    @Test
    public void name() throws IOException, MethodNotSupportedException {
        HttpResponse res = esService.executeQuery(esUrl + "/_msearch", "{\"index\":[\"mel_com_private_pv_menu_20180106\",\"mel_com_private_pv_menu_20180107\",\"mel_com_private_pv_menu_20180105\"],\"ignore_unavailable\":true,\"preference\":1515386138014}\n" +
                "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true}},{\"match_phrase\":{\"menu_1d_nm\":{\"query\":\"재생목록\"}}},{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true}},{\"range\":{\"ts\":{\"gte\":1515078000000,\"lte\":1515337199999,\"format\":\"epoch_millis\"}}}],\"must_not\":[]}},\"size\":0,\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"ts\",\"interval\":\"1d\",\"time_zone\":\"Asia/Tokyo\",\"min_doc_count\":1},\"aggs\":{\"3\":{\"terms\":{\"field\":\"menu_1d_nm\",\"size\":17,\"order\":{\"1\":\"desc\"}},\"aggs\":{\"1\":{\"sum\":{\"field\":\"datapoint\"}}}}}}},\"version\":true,\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{\"highlight_query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true,\"all_fields\":true}},{\"match_phrase\":{\"menu_1d_nm\":{\"query\":\"재생목록\"}}},{\"query_string\":{\"query\":\"*\",\"analyze_wildcard\":true,\"all_fields\":true}},{\"range\":{\"ts\":{\"gte\":1515078000000,\"lte\":1515337199999,\"format\":\"epoch_millis\"}}}],\"must_not\":[]}}}},\"fragment_size\":2147483647}}\n");
        System.out.println(EntityUtils.toString(res.getEntity()));
    }
}
