package com.louishhy.paperlinkbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "graph")
@Data // Lombok getter-setter helper
public class Graph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "graph",
            fetch = FetchType.LAZY
    )
    private List<GraphNode> nodes = new ArrayList<>();

    @OneToMany(
            mappedBy = "graph",
            fetch = FetchType.LAZY
    )
    private List<GraphEdge> edges = new ArrayList<>();
}
