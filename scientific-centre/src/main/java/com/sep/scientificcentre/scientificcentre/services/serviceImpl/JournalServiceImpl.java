package com.sep.scientificcentre.scientificcentre.services.serviceImpl;

import com.sep.scientificcentre.scientificcentre.entity.Journal;
import com.sep.scientificcentre.scientificcentre.repository.JournalRepository;
import com.sep.scientificcentre.scientificcentre.services.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;

    @Autowired
    public JournalServiceImpl(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Override
    public List<Journal> findAll() {
        return journalRepository.findAll();
    }

    @Override
    public Journal findByName(String name) {
        return journalRepository.findByName(name);
    }

    @Override
    public Journal findById(Long journalId) {
        return journalRepository.getOne(journalId);
    }

    @Override
    public List<Journal> findAllCompanyJournals(){
        return null;
    }
}
