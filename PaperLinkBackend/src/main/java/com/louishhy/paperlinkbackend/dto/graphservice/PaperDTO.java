package com.louishhy.paperlinkbackend.dto.graphservice;

import com.louishhy.paperlinkbackend.model.Paper;
import lombok.Data;

@Data
public class PaperDTO {
    private long id;
    private String title;
    private String authors;
    private String doi;
    private String paperAbstract;
    private String publication;
    private String url;

    public static PaperDTO fromEntity(Paper paper) {
        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setId(paper.getId());
        paperDTO.setTitle(paper.getTitle());
        paperDTO.setAuthors(paper.getAuthors());
        paperDTO.setDoi(paper.getDoi());
        paperDTO.setPaperAbstract(paper.getPaperAbstract());
        paperDTO.setPublication(paper.getPublication());
        paperDTO.setUrl(paper.getUrl());
        return paperDTO;
    }
}
