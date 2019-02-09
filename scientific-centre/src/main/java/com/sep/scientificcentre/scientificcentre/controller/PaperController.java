package com.sep.scientificcentre.scientificcentre.controller;

import com.sep.scientificcentre.scientificcentre.entity.PaidJournal;
import com.sep.scientificcentre.scientificcentre.entity.Paper;
import com.sep.scientificcentre.scientificcentre.entity.User;
import com.sep.scientificcentre.scientificcentre.entity.dto.FinishPaymentDto;
import com.sep.scientificcentre.scientificcentre.services.PaperService;
import com.sep.scientificcentre.scientificcentre.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    private final PaperService paperService;
    private final UserService userService;

    @Autowired
    public PaperController(PaperService paperService, UserService userService) {
        this.paperService = paperService;
        this.userService = userService;
    }


    /**
     * Get all papers from the db.
     *
     * @return list of papers
     */
    @GetMapping(value = "{username}")
    public ResponseEntity<Set<Paper>> getPapers(@PathVariable String username) {
        return new ResponseEntity<>(paperService.getAll(username), HttpStatus.OK);
    }

    /**
     * Add new paper.
     *
     * @param finishPaymentDto journal name, paper id, client's username and type of payment
     */
    @PostMapping(value = "/add")
    public void addPaper(@RequestBody FinishPaymentDto finishPaymentDto) {
        User user = userService.getByUsername(finishPaymentDto.getUsername());
        Paper paper = paperService.getOne(finishPaymentDto.getPaperId());
        user.getPapers().add(paper);
        userService.create(user);
    }

    /**
     * Get user's bought papers.
     *
     * @param username username
     * @return list of the user's papers
     */
    @GetMapping(value = "/my-papers/{username}")
    public ResponseEntity<Set<Paper>> getMyPapers(@PathVariable String username) {
        User user = userService.getByUsername(username);
        Set<Paper> myPapers = new HashSet<>();
        myPapers.addAll(user.getPapers());
        List<PaidJournal> journals = user.getJournals();
        for (PaidJournal j : journals) {
            if (j.getActivityDate().after(new Date())) {
                myPapers.addAll(paperService.getByJournalName(j.getJournal().getName()));
            }
        }
        return new ResponseEntity<>(myPapers, HttpStatus.OK);
    }

}
