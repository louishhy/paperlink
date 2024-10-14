package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
    Optional<Paper> findByDoi(String doi);
    List<Paper> findByTitleContaining(String title);
}
