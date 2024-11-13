package com.louishhy.paperlinkbackend.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louishhy.paperlinkbackend.dto.crossref.CrossrefWork;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CrossrefClient {
    private static final String BASE_URL = "https://api.crossref.org";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${app.crossref.email}")
    private String email;

    public CrossrefWork getWorkByDoi(String doi) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/works/" + doi))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("User-Agent", "PaperlinkDB/0.1 (mailto: " + email + ")")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode message = root.get("message");
            return objectMapper.treeToValue(message, CrossrefWork.class);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Crossref work by DOI " + doi, e);
        }
    }
}
