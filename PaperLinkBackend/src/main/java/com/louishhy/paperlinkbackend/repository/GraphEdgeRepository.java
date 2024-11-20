package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.GraphEdge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphEdgeRepository extends JpaRepository<GraphEdge, Long> {
    List<GraphEdge> findByGraphId(long graphId);

    boolean existsByGraphIdAndCitingPaperIdAndCitedPaperId(long graphId, long citingPaperId, long citedPaperId);
}
