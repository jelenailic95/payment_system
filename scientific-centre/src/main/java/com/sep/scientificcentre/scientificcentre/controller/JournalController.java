package com.sep.scientificcentre.scientificcentre.controller;

import com.sep.scientificcentre.scientificcentre.entity.dto.JournalDto;
import com.sep.scientificcentre.scientificcentre.entity.dto.JournalsDto;
import com.sep.scientificcentre.scientificcentre.services.JournalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/journals")
public class JournalController {

    private final JournalService journalService;
    private final ModelMapper modelMapper;
    @Autowired
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<JournalsDto> getJournals() throws IOException {
        List<JournalDto> journalDtos = new ArrayList<>();
        journalService.findAll().forEach(j -> journalDtos.add(modelMapper.map(j, JournalDto.class)));
        JournalsDto journalsDto = new JournalsDto(journalDtos);
        return new ResponseEntity<>(journalsDto, HttpStatus.OK);
    }




}
