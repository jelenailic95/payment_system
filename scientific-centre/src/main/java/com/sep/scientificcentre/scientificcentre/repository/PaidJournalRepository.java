package com.sep.scientificcentre.scientificcentre.repository;

import com.sep.scientificcentre.scientificcentre.entity.PaidJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaidJournalRepository extends JpaRepository<PaidJournal, Long> {
}
