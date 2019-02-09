package com.sep.scientificcentre.scientificcentre.services.serviceImpl;

import com.sep.scientificcentre.scientificcentre.entity.PaidJournal;
import com.sep.scientificcentre.scientificcentre.entity.Paper;
import com.sep.scientificcentre.scientificcentre.entity.User;
import com.sep.scientificcentre.scientificcentre.repository.PaperRepository;
import com.sep.scientificcentre.scientificcentre.repository.UserRepository;
import com.sep.scientificcentre.scientificcentre.services.PaperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperRepository paperRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public PaperServiceImpl(PaperRepository paperRepository, UserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public Paper getOne(Long id) {
        return paperRepository.getOne(id);
    }

    @Override
    public Set<Paper> getAll(String username) {
        User user = userRepository.findByUsername(username);
        Set<Paper> papers = new HashSet<>();
        for(Paper paper: paperRepository.findAll()){
            PaidJournal paidJournal = modelMapper.map(paper.getJournal(), PaidJournal.class);
            if(!user.getPapers().contains(paper) && !user.getJournals().contains(paidJournal)){
                papers.add(paper);
            }
        }
        return papers;
    }

    @Override
    public List<Paper> getByJournalName(String name) {
        return paperRepository.getPaperByJournalName(name);
    }
}
