package com.louishhy.paperlinkbackend.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louishhy.paperlinkbackend.dto.crossref.CrossrefWork;
import com.louishhy.paperlinkbackend.exception.crossref.CrossrefNotFoundException;
import com.louishhy.paperlinkbackend.exception.crossref.CrossrefServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Slf4j
public class CrossrefClient {
    private static final String BASE_URL = "https://api.crossref.org";
    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${app.crossref.email}")
    private String email;

    public CrossrefClient() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, String.format("Paperlink-Backend/0.1 (mailto: %s)", email))
                .build();
    }


    public CrossrefWork getWorkByDoi(String doi) throws CrossrefNotFoundException {
        String url = "/works/" + doi;
        log.info("Get work by doi: {}", url);
        try {
            ResponseEntity<String> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new CrossrefNotFoundException(
                                    String.format("Crossref %s not found", doi)
                            );
                        }
                        else {
                            throw new RuntimeException("Unexpected response status: " + res.getStatusCode());
                        }
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new CrossrefServerErrorException("Crossref server error");
                    })
                    .toEntity(String.class);
            String responseBody = response.getBody();
            HttpStatusCode statusCode = response.getStatusCode();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode message = root.get("message");
            return objectMapper.treeToValue(message, CrossrefWork.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Error getting Crossref work by DOI " + doi, e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing Crossref response " + doi, e);
        }
    }
}
