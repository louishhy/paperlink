package com.louishhy.paperlinkbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louishhy.paperlinkbackend.dto.crossref.CrossrefWork;
import com.louishhy.paperlinkbackend.model.Paper;
import com.louishhy.paperlinkbackend.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaperService {
    private final PaperRepository paperRepository;
    private final WebClient crossrefClient;

    public PaperService(@Autowired PaperRepository paperRepository,
                        @Qualifier("crossrefClient") WebClient crossrefClient) {
        this.paperRepository = paperRepository;
        this.crossrefClient = crossrefClient;
    }

    public Mono<CrossrefWork> getCrossrefWorkByDoi(String doi) {
        return crossrefClient.get()
                .uri("/works/{doi}", doi)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(root -> root.get("message"))
                .flatMap(message -> {
                    try {
                        return Mono.just(new ObjectMapper().treeToValue(message, CrossrefWork.class));
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                })
                .doOnError(error -> log.error("Error getting Crossref work by DOI {}: {}", doi, error.getMessage()));
    }


    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }


    public void deletePaper(long id) {
        paperRepository.deleteById(id);
    }


}
