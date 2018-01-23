package com.elasticsearchcache;

import com.elasticsearchcache.service.ElasticSearchService;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ByteArrayEntity;
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
public class CreateIndexTest {
    @Autowired
    private ElasticSearchService esService;

    @Value("${zuul.routes.**.url}")
    private String esUrl;

    @Test
    public void name() {
        String body =
                "{\n" +
                        "  \"settings\": {\n" +
                        "    \"number_of_shards\": 3,\n" +
                        "    \"number_of_replicas\": 1,\n" +
                        "    \"refresh_interval\": \"10s\"\n" +
                        "  },\n" +
                        "  \"mappings\": {\n" +
                        "    \"info\": {\n" +
                        "      \"_all\": {\n" +
                        "        \"enabled\": false\n" +
                        "      },\n" +
                        "      \"_source\": {\n" +
                        "        \"enabled\": true\n" +
                        "      },\n" +
                        "      \"properties\": {\n" +
                        "        \"key\": {\n" +
                        "          \"type\": \"keyword\",\n" +
                        "          \"store\": false,\n" +
                        "          \"doc_values\": true\n" +
                        "        },\n" +
                        "        \"value\": {\n" +
                        "          \"type\": \"keyword\",\n" +
                        "          \"store\": false,\n" +
                        "          \"doc_values\": false\n" +
                        "        },\n" +
                        "        \"ts\": {\n" +
                        "          \"type\": \"date\",\n" +
                        "          \"store\": false\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"dynamic_templates\": [\n" +
                        "        {\n" +
                        "          \"unindexed_text\": {\n" +
                        "            \"match_mapping_type\": \"text\",\n" +
                        "            \"mapping\": {\n" +
                        "              \"type\": \"keyword\",\n" +
                        "              \"index\": true,\n" +
                        "              \"doc_values\": true,\n" +
                        "              \"store\": false\n" +
                        "            }\n" +
                        "          }\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"unindexed_text\": {\n" +
                        "            \"match_mapping_type\": \"string\",\n" +
                        "            \"mapping\": {\n" +
                        "              \"type\": \"keyword\",\n" +
                        "              \"index\": true,\n" +
                        "              \"doc_values\": true,\n" +
                        "              \"store\": false\n" +
                        "            }\n" +
                        "          }\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        System.out.println("yesyes = " + esUrl + "/esc_profile");
        try {
            esService.executeHttpRequest(HttpMethod.PUT, esUrl + "/esc_profile", new ByteArrayEntity(body.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MethodNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
