package com.example.Tourlist_spot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        // 기본적인 RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // XML 변환기 추가
        restTemplate.getMessageConverters().add(new Jaxb2RootElementHttpMessageConverter());

        return restTemplate;
    }
}
