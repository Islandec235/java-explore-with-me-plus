package ru.yandex.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebClientConfig {
    public String baseUrl;

    public WebClientConfig(@Value("${stats-server.url:http://localhost:9090}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
