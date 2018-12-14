package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentMethodRepository;
import com.sep.payment.paymentconcentrator.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    public ClientServiceImpl(ClientRepository clientRepository, PaymentMethodRepository paymentMethodRepository) {
        this.clientRepository = clientRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<Client> getAllMethods(String client) {
        return clientRepository.findByClient(client);
    }

    @Override
    public Client addNewMethod(String clientName, String clientId, String clientPassword, String method) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByName(method);
        Client newClient = new Client(clientName, clientName, clientId, clientPassword, paymentMethod);
        return clientRepository.save(newClient);
    }

    @Override
    public Client findByClientMethod(String client, String method) {
        return clientRepository.findByClientAndPaymentMethodName(client, method);
    }
}
