package com.elasticsearchcache.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * ES 클라이언트 설정
 *
 * @author lks21c
 */
@ComponentScan("com.elasticsearchcache")
@Configuration
public class EsConfig {
    private Logger logger = LoggerFactory.getLogger(EsConfig.class);

    private static String profile = System.getProperty("spring.profile.active");

    @Value("${zuul.routes.**.url}")
    private String esUrl;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient transportClient() throws UnknownHostException {
        String url = esUrl.replace("http://", "").replace("https://","");
        String[] urlArr = url.split(":");
        String parsedUrl = urlArr[0];
        int parsedPort = Integer.valueOf(urlArr[1]);
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(parsedUrl, parsedPort, "http")));
        return client;
    }
}
