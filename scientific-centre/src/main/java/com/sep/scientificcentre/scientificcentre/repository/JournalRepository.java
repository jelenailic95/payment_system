package com.sep.scientificcentre.scientificcentre.repository;

import com.sep.scientificcentre.scientificcentre.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal,Long> {

    Journal findByName(String name);
}
