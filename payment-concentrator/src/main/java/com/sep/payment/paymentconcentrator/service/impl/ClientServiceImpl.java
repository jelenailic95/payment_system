package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentMethodRepository;
import com.sep.payment.paymentconcentrator.security.AES;
import com.sep.payment.paymentconcentrator.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final AES aes;

    public ClientServiceImpl(ClientRepository clientRepository, PaymentMethodRepository paymentMethodRepository, AES aes) {
        this.clientRepository = clientRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.aes = aes;
    }

    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public List<Client> getAllMethods(String client) {
        return clientRepository.findByJournal(client);
    }

    @Override
    public Client methodSubscribe(String clientName, String clientId, String clientPassword, String method,
                                  String methodName) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByMethodNameAndMethod(methodName, method);

        if (paymentMethod == null) {
            return null;
        }

        Client client = this.findByClientMethod(clientName, methodName);
        String encryptedClientId = aes.encrypt(clientId);
        if (!clientPassword.equals("")) {
            clientPassword = aes.encrypt(clientPassword);
        }

        System.out.println("KRIPTOVANO: " + encryptedClientId);
        System.out.println("KRIPTOVANO: " + clientPassword);

        if (client == null) {
            Client newClient = new Client(clientName, clientName, encryptedClientId, clientPassword, paymentMethod);
            clientRepository.save(newClient);

            logger.info("Client {} has successfully enabled {}.", clientName, method);
            return clientRepository.save(newClient);
        }

        client.setClientId(encryptedClientId);
        client.setClientPassword(clientPassword);

        // todo: testirati
        client.getPaymentMethod().setMethodName(methodName);

        clientRepository.save(client);
        logger.info("Client {} has successfully changed credentials for the {}.", clientName, method);
        return client;
    }

    @Override
    public void methodUnsubscribe(String client, String method, String methodName) {
        Client clientDb = clientRepository.findByJournalAndPaymentMethodMethodAndPaymentMethodMethodName(client, method,
                methodName);

        clientRepository.delete(clientDb);
    }


    @Override
    public Client findByClientMethod(String client, String method) {
        return clientRepository.findByJournalAndPaymentMethodMethodName(client, method);
    }

    @Override
    public List<PaymentMethod> findPaymentMethodByMethod(String method) {
        return paymentMethodRepository.findByMethod(method);
    }
}
