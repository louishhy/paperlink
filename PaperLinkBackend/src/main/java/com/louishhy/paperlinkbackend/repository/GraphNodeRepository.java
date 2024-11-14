package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.GraphNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphNodeRepository extends JpaRepository<GraphNode, Long> {
    List<GraphNode> findByGraphId(Long graphId);
}
