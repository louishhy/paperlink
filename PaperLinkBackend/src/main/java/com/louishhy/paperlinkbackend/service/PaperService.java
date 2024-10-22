package com.louishhy.paperlinkbackend.service;

import com.louishhy.paperlinkbackend.model.Paper;
import com.louishhy.paperlinkbackend.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {
    private final PaperRepository paperRepository;

    @Autowired
    public PaperService(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }


}
