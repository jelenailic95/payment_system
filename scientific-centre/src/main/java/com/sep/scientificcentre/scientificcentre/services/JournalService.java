package com.sep.scientificcentre.scientificcentre.services;

import com.sep.scientificcentre.scientificcentre.entity.Journal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JournalService {

    List<Journal> findAll();
    Journal findByName(String name);

    Journal findById(Long journalId);
    List<Journal> findAllCompanyJournals();
}
