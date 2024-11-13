package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.GraphEdge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphEdgeRepository extends JpaRepository<GraphEdge, Long> {
}
