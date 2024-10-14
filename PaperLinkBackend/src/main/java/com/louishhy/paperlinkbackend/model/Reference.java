package com.louishhy.paperlinkbackend.model;

import jakarta.persistence.*;

@Entity
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "citing_paper_id", nullable = false)
    private Paper citingPaper;

    @ManyToOne
    @JoinColumn(name = "referenced_paper_id", nullable = false)
    private Paper referencedPaper;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Paper getCitingPaper() {
        return citingPaper;
    }

    public void setCitingPaper(Paper citingPaper) {
        this.citingPaper = citingPaper;
    }

    public Paper getReferencedPaper() {
        return referencedPaper;
    }

    public void setReferencedPaper(Paper referencedPaper) {
        this.referencedPaper = referencedPaper;
    }
}
