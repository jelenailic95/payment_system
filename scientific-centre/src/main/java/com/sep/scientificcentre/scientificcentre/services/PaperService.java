package com.sep.scientificcentre.scientificcentre.services;

import com.sep.scientificcentre.scientificcentre.entity.Paper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PaperService {

    Paper getOne(Long id);
    Set<Paper> getAll(String username);
    List<Paper> getByJournalName(String name);
}
