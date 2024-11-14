package com.louishhy.paperlinkbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "graph_edge")
@Data
public class GraphEdge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graph_id", nullable = false)
    private Graph graph;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citing_paper_id", nullable = false)
    private Paper citingPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cited_paper_id", nullable = false)
    private Paper citedPaper;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}
