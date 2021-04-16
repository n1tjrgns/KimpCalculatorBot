package com.kimp.calculatorbot.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class UpbitConfig {

    @Bean
    public RestTemplate restTemplate(){

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(20) // connection pool 적용
                .setMaxConnPerRoute(10) // connection pool 적용
                .build();

        factory.setHttpClient(httpClient);
        factory.setConnectTimeout(5000); //타임아웃 설정 5초
        factory.setReadTimeout(5000);//타임아웃 설정 5초

        // TO-DO 추후 DB 사용시 HttpClient 설정 필요

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

        return restTemplate;
    }
}
