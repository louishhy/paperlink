package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.GraphNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphNodeRepository extends JpaRepository<GraphNode, Long> {
}
