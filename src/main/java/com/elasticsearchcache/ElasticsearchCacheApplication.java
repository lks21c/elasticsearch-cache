package com.elasticsearchcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableZuulProxy
@Configuration
public class ElasticsearchCacheApplication {

	public static void main(String[] args) {
        System.setProperty("file.encoding","UTF-8");
        SpringApplication application = new SpringApplication(ElasticsearchCacheApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
	}

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

//    @Bean
//    public PostFilter postFilter() {
//        return new PostFilter();
//    }
}
