package com.elasticsearchcache.service;

import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticSearchService {
    private static final Logger logger = LogManager.getLogger(ElasticSearchService.class);

    @Value("${httpclient.timeout}")
    private int timeout;

    private RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(timeout * 1000)
            .setConnectionRequestTimeout(timeout * 1000)
            .setSocketTimeout(timeout * 1000).build();

    public HttpResponse executeHttpRequest(HttpMethod requestType, String url, ByteArrayEntity entity) throws IOException, MethodNotSupportedException {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpResponse httpResponse;

        if (entity != null) {
            entity.setContentType("application/json");
        }
        switch (requestType) {
            case POST:
                HttpPost post = new HttpPost(url);
                post.setEntity(entity);
                httpResponse = client.execute(post);
                break;
            case GET:
                HttpGet httpGet = new HttpGet(url);
                httpResponse = client.execute(httpGet);
                break;
            case DELETE:
                HttpDelete httpDelete = new HttpDelete(url);
                httpResponse = client.execute(httpDelete);
                break;
            case PUT:
                HttpPut httpput = new HttpPut(url);
                httpput.setEntity(entity);
                httpResponse = client.execute(httpput);
                break;
            default:
                throw new MethodNotSupportedException(requestType.toString());
        }
        return httpResponse;
    }

    public HttpResponse executeQuery(String targetUrl, String reqBody) throws IOException, MethodNotSupportedException {
        logger.debug("curl -X POST -L '" + targetUrl + "' " + " --data '" + reqBody + "'");

        HttpResponse res = executeHttpRequest(HttpMethod.POST, targetUrl, new ByteArrayEntity(reqBody.getBytes()));
        return res;
    }
}
