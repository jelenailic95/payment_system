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

    /**
     * Subscribe for the payment method.
     *
     * @param clientName     journal name
     * @param clientId       client username for the payment method
     * @param clientPassword client password for the payment method
     * @param method         method
     * @param methodName     method name
     * @return client
     */
    @Override
    public Client methodSubscribe(String clientName, String clientId, String clientPassword, String method,
                                  String methodName) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByMethodNameAndMethod(methodName, method);

        if (paymentMethod == null) {
            return null;
        }

        String encryptedClientId = "";

        Client client = clientRepository.findByJournalAndPaymentMethodMethod(clientName, method);

        if (!method.equals("paypal")) {
            encryptedClientId = aes.encrypt(clientId);
            if (!clientPassword.equals("")) {
                clientPassword = aes.encrypt(clientPassword);
            }
        }

        if (client == null) {
            Client newClient;
            if (method.equals("paypal")) {
                newClient = new Client(clientName, clientName, clientId, clientPassword, paymentMethod);
            } else {
                newClient = new Client(clientName, clientName, encryptedClientId, clientPassword, paymentMethod);
            }
            clientRepository.save(newClient);

            logger.info("Client {} has successfully enabled {}.", clientName, method);
            return clientRepository.save(newClient);
        }

        if (method.equals("paypal")) {
            client.setClientId(clientId);
        }else{
            client.setClientId(encryptedClientId);
        }

        client.setClientPassword(clientPassword);

        PaymentMethod paymentMethodDb = paymentMethodRepository.findByMethodNameAndMethod(methodName, method);
        client.setPaymentMethod(paymentMethodDb);

        clientRepository.save(client);
        logger.info("Client {} has successfully changed credentials for the {}.", clientName, method);
        return client;
    }

    /**
     * Unsubscribe from the payment method.
     *
     * @param client     client
     * @param method     method
     * @param methodName method name
     */
    @Override
    public void methodUnsubscribe(String client, String method, String methodName) {
        Client clientDb = clientRepository.findByJournalAndPaymentMethodMethodAndPaymentMethodMethodName(client, method,
                methodName);

        clientRepository.delete(clientDb);
    }


    /**
     * Find client with the given journal and payment method name.
     *
     * @param client journal
     * @param method method
     * @return client
     */
    @Override
    public Client findByClientMethod(String client, String method) {
        return clientRepository.findByJournalAndPaymentMethodMethodName(client, method);
    }

    /**
     * Find payment method that has given method.
     *
     * @param method method
     * @return list of payment methods
     */
    @Override
    public List<PaymentMethod> findPaymentMethodByMethod(String method) {
        return paymentMethodRepository.findByMethod(method);
    }
}
