package com.sep.scientificcentre.scientificcentre.services.serviceImpl;

import com.sep.scientificcentre.scientificcentre.entity.Paper;
import com.sep.scientificcentre.scientificcentre.repository.PaperRepository;
import com.sep.scientificcentre.scientificcentre.services.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperRepository paperRepository;

    @Autowired
    public PaperServiceImpl(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }


    @Override
    public Paper getOne(Long id) {
        return paperRepository.getOne(id);
    }

    @Override
    public List<Paper> getAll() {
        return paperRepository.findAll();
    }
}
