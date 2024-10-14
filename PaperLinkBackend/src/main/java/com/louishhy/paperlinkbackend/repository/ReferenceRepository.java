package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
}
