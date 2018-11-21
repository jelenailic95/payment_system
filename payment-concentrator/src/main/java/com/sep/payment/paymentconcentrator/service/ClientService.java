package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {

    Client findByJournal(String journal);
}
