package com.louishhy.paperlinkbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "graph_edge")
@Data
public class GraphEdge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @OneToOne()
}
