package com.sep.scientificcentre.scientificcentre.services;

import com.sep.scientificcentre.scientificcentre.entity.Paper;
import org.springframework.stereotype.Service;

@Service
public interface PaperService {

    Paper getOne(Long id);
}
