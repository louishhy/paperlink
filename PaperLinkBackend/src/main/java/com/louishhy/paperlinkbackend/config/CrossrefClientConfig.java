package com.louishhy.paperlinkbackend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class CrossrefClientConfig {
    @Bean
    @Qualifier("crossrefClient")
    public WebClient crossrefClient() {
        return WebClient.builder()
                .baseUrl("https://api.crossref.org")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("User-Agent", "PaperlinkDB/0.1 (mailto: breezelo2000@gmail.com)")
                .build();
    }
}
