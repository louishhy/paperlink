package com.louishhy.paperlinkbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "authors", nullable = false, length = 500)
    private String authors;

    @Column(name = "doi", nullable = false, length = 255)
    private String doi;

    @Column(name = "paper_abstract", nullable = false, columnDefinition = "TEXT")
    private String paperAbstract;

    @Column(name = "publication", nullable = false, length = 255)
    private String publication;

    @Column(name = "url", nullable = false, length = 500)
    private String url;
}
