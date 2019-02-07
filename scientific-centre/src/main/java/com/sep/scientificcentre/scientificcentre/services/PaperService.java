package com.sep.scientificcentre.scientificcentre.services;

import com.sep.scientificcentre.scientificcentre.entity.Paper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaperService {

    Paper getOne(Long id);
    List<Paper> getAll();
    List<Paper> getByJournalName(String name);
}
