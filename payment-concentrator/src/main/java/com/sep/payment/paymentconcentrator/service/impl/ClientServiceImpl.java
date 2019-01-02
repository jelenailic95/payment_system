package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.controller.PaymentMethodController;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentMethodRepository;
import com.sep.payment.paymentconcentrator.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public List<Client> getAllMethods(String client) {
        return clientRepository.findByClient(client);
    }

    @Override
    public Client addNewMethod(String clientName, String clientId, String clientPassword, String method) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByName(method);

        // hashed client password
        String hashedPassword = new BCryptPasswordEncoder().encode(clientPassword);
        Client newClient = new Client(clientName, clientName, clientId, hashedPassword, paymentMethod);

        logger.info("Client has registered for the: {}", paymentMethod.getName());

        return clientRepository.save(newClient);
    }

    @Override
    public Client findByClientMethod(String client, String method) {
        return clientRepository.findByClientAndPaymentMethodName(client, method);
    }
}
