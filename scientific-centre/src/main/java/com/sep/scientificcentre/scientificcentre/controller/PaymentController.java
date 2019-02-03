package com.sep.scientificcentre.scientificcentre.controller;

import com.sep.scientificcentre.scientificcentre.entity.Journal;
import com.sep.scientificcentre.scientificcentre.entity.PaidJournal;
import com.sep.scientificcentre.scientificcentre.entity.Paper;
import com.sep.scientificcentre.scientificcentre.entity.User;
import com.sep.scientificcentre.scientificcentre.entity.dto.FinishPaymentDto;
import com.sep.scientificcentre.scientificcentre.repository.PaidJournalRepository;
import com.sep.scientificcentre.scientificcentre.services.JournalService;
import com.sep.scientificcentre.scientificcentre.services.PaperService;
import com.sep.scientificcentre.scientificcentre.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/nc1")
public class PaymentController {

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaperService paperService;
    private final JournalService journalService;
    private final PaidJournalRepository paidJournalRepository;
    private final UserService userService;

    @Autowired
    PaymentController(PaperService paperService, JournalService journalService, UserService userService,
                      PaidJournalRepository paidJournalRepository){
        this.paperService = paperService;
        this.journalService = journalService;
        this.paidJournalRepository = paidJournalRepository;
        this.userService = userService;
    }

    @PostMapping(value = "/successful-payment")
    public void paymentSuccessful(@RequestBody FinishPaymentDto finishPaymentDto) {
        logger.info("Request - successful payment.");

        Long userId = 1L;
        // imace ili paperId ili journalId
        Long paperId = 1L;
        Long journalId = 1L;
        String typeOfPayment = "journal";
        User user = userService.getOne(userId);
        if(typeOfPayment.equals("journal")){
            Journal journal = journalService.findById(journalId);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, journal.getPeriod());
            Date result = cal.getTime();

            PaidJournal paidJournal = PaidJournal.builder().journal(journal).activityDate(result).build();
            user.getJournals().add(paidJournalRepository.save(paidJournal));

        }else{
            Paper paper = paperService.getOne(paperId);
            user.getPapers().add(paper);
        }


    }
}
