package com.louishhy.paperlinkbackend.controller;

import com.louishhy.paperlinkbackend.exception.crossref.CrossrefNotFoundException;
import com.louishhy.paperlinkbackend.model.Paper;
import com.louishhy.paperlinkbackend.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/papers")
public class PaperController {
    private final PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping
    public ResponseEntity<List<Paper>> getAllPapers() {
        return ResponseEntity.ok(paperService.getAllPapers());
    }

    @PostMapping("/add-doi")
    public ResponseEntity<Paper> doi(@RequestParam String doi) {
        try {
            Paper paper = paperService.addPaperByDoi(doi);
            return ResponseEntity.ok(paper);
        } catch (CrossrefNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
