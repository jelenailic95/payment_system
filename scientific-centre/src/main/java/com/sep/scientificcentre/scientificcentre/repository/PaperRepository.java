package com.sep.scientificcentre.scientificcentre.repository;

import com.sep.scientificcentre.scientificcentre.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
}
