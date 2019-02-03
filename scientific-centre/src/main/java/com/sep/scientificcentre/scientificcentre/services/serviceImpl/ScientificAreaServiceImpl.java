package com.sep.scientificcentre.scientificcentre.services.serviceImpl;

import com.upp.scientificcentre.entity.ScientificArea;
import com.upp.scientificcentre.repository.jpa.ScientificAreaRepository;
import com.upp.scientificcentre.service.jpa.ScientificAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificAreaServiceImpl implements ScientificAreaService {

    private final ScientificAreaRepository scientificAreaRepository;

    @Autowired
    public ScientificAreaServiceImpl(ScientificAreaRepository scientificAreaRepository) {
        this.scientificAreaRepository = scientificAreaRepository;
    }

    @Override
    public List<ScientificArea> getAllAreas() {
        return scientificAreaRepository.findAll();
    }
}
