package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.Graph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphRepository extends JpaRepository<Graph, Long> {
}
