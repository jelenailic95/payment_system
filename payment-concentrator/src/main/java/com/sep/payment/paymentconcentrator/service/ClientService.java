package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    List<Client> getAllMethods(String client);

    Client addNewMethod(String clientName, String clientId, String clientPassword, String method);
}
