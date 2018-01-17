package com.elasticsearchcache.config;

import com.elasticsearchcache.service.ElasticSearchService;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ByteArrayEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class PostCreation {

    @Autowired
    private ElasticSearchService esService;

    @Value("${zuul.routes.proxy.url}")
    private String esUrl;

    @Bean
    @PostConstruct
    public String createProfileIndex() {
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
        try {
            esService.executeHttpRequest(HttpMethod.PUT, esUrl + "/esc_profile", new ByteArrayEntity(body.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MethodNotSupportedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @Bean
    @PostConstruct
    public String createCacheIndex() {
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
                        "          \"type\": \"binary\",\n" +
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
        try {
            esService.executeHttpRequest(HttpMethod.PUT, esUrl + "/esc_cache", new ByteArrayEntity(body.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MethodNotSupportedException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
