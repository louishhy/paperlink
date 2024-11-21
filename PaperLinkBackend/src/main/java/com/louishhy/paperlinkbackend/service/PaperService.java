package com.louishhy.paperlinkbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louishhy.paperlinkbackend.dto.crossref.CrossrefWork;
import com.louishhy.paperlinkbackend.integration.CrossrefClient;
import com.louishhy.paperlinkbackend.model.Paper;
import com.louishhy.paperlinkbackend.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class PaperService {
    private final PaperRepository paperRepository;
    private final CrossrefClient crossrefClient;

    @Autowired
    public PaperService(PaperRepository paperRepository,
                        CrossrefClient crossrefClient) {
        this.paperRepository = paperRepository;
        this.crossrefClient = crossrefClient;
    }

    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }

    @Transactional
    public Paper addPaperByDoi(String doi) {
        CrossrefWork crossrefWork = this.crossrefClient.getWorkByDoi(doi);
        Paper paper = new Paper();
        // Special processing for saving items
        String authorsStr = crossrefWork.getAuthor().stream()
                .map(a -> a.getFamily() + " " + a.getGiven())
                .collect(Collectors.joining(", "));
        String paperTitle = crossrefWork.getTitle().get(0);
        String containerTitle = crossrefWork.getContainerTitle().get(0);
        paper.setDoi(crossrefWork.getDoi());
        paper.setTitle(paperTitle);
        paper.setAuthors(authorsStr);
        paper.setPublication(containerTitle);
        paper.setPaperAbstract(crossrefWork.getAbstract_());
        paper.setUrl(crossrefWork.getUrl());
        // Persist paper
        return paperRepository.save(paper);
    }

    @Transactional
    public void deletePaper(long id) {
        if (!paperRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paper not found");
        }
        paperRepository.deleteById(id);
    }


}
