package com.sep.payment.paymentconcentrator.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sep.payment.paymentconcentrator.domain.dto.*;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.Constants;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.exception.NotFoundException;
import com.sep.payment.paymentconcentrator.security.AES;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping(value = "/pc")
public class PaymentMethodController {

    @Value(value = "${auth.host}")
    private String authProxy;

    private final ClientService clientService;
    private final RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    private final AES aes;


    private Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);

    public PaymentMethodController(ClientService clientService, RestTemplate restTemplate, AES aes) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
        this.aes = aes;
    }

    /**
     * POST: Get all payment method for the given journal.
     *
     * @param clientDTO tokenized journal name
     * @return all payment methods for the given journal
     * @throws UnsupportedEncodingException encoding problem
     */
    @PostMapping(value = "/payment-methods")
    public ResponseEntity checkPaymentMethods(@RequestBody @Valid ClientDTO clientDTO)
            throws UnsupportedEncodingException {
        logger.info("Request - return all possible payment methods.");

        String token = Utility.readToken(clientDTO.getClientId());
        String clientName = token.split("-")[2];

        List<Client> clients = clientService.getAllMethods(clientName);

        if (clients.size() == 0) {
            logger.info("This client doesn't have any registered payment methods.");
            return ResponseEntity.ok(clients);
        } else {
            Collections.sort(clients, Comparator.comparing(Client::getClient));
        }

        Set<PaymentMethodDTO> paymentMethodDTOS = new HashSet<>();

        Objects.requireNonNull(clients).forEach(client -> paymentMethodDTOS.add(modelMapper.map(client.getPaymentMethod(),
                PaymentMethodDTO.class)));
        ResponseEntity<Object> res = restTemplate.postForEntity(authProxy + "/auth", clientDTO, Object.class);
        logger.info("This client has registered payment methods.");

        PaymentMethodsAndTokenDTO response = PaymentMethodsAndTokenDTO.builder().paymentMethodDTOS(paymentMethodDTOS).
                amount(Double.parseDouble(token.split("-")[3]))
                .token(res.getHeaders().get("Authorization").get(0)).build();

        return ResponseEntity.ok(response);
    }

    /**
     * POST: Subscribe for the new payment method.
     *
     * @param newMethodDTO payment method object
     * @return company with the new payment method subscribed
     * @throws UnsupportedEncodingException encoding problems
     */
    @PostMapping(value = "/method-subscribe")
    public ResponseEntity enableNewPaymentMethod(@RequestBody @Valid NewMethodDTO newMethodDTO)
            throws UnsupportedEncodingException {

        logger.info("Request - enable {} for the client {}", newMethodDTO.getMethod(), newMethodDTO.getClientName());

        String token = Utility.readToken(newMethodDTO.getClientName());
        String clientName = token.split("-")[2];

        Client client = clientService.methodSubscribe(clientName, newMethodDTO.getClientId(),
                newMethodDTO.getClientPassword(), newMethodDTO.getMethod(), newMethodDTO.getMethodName());

        logger.info("Payment method is successfully enabled.");

        if (client == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(client);
    }

    /**
     * POST: Unsubscribe from the payment method.
     *
     * @param requestDTO payment method
     * @return response status
     * @throws UnsupportedEncodingException encoding problems
     */
    @PostMapping(value = "/method-unsubscribe")
    public ResponseEntity methodUnsubscribe(@RequestBody @Valid PaymentMethodDetailsRequestDTO requestDTO)
            throws UnsupportedEncodingException {
        logger.info("Request - unsubscribe {} for the client {}", requestDTO.getMethod(), requestDTO.getClientId());
        String token = Utility.readToken(requestDTO.getClientId());
        String clientName = token.split("-")[2];

        clientService.methodUnsubscribe(clientName, requestDTO.getMethod(), requestDTO.getMethodName());

        logger.info("{} is successfully unsubscribe.", requestDTO.getMethod());

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get-token/{name}")
    public ResponseEntity<String> getToken(@PathVariable String name) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        String token = JWT.create()
                .withClaim("client", name)
                .sign(algorithm);
        return ResponseEntity.ok(token);
    }

    /**
     * POST: Get details for the given payment method.
     *
     * @param requestDTO payment method and payment method name
     * @return details of the payment method
     * @throws UnsupportedEncodingException encoding problems
     */
    @PostMapping(value = "/payment-method-details")
    public ResponseEntity getPaymentMethodDetails(@RequestBody PaymentMethodDetailsRequestDTO requestDTO)
            throws UnsupportedEncodingException {
        logger.info("Request- get payment method details.");
        String token = Utility.readToken(requestDTO.getClientId());
        String clientName = token.split("-")[2];

        // get client with its registered payment method
        Client foundClient = clientService.findByClientMethod(clientName, requestDTO.getMethodName());
        logger.info("Get payment method details for the {}, for the client: {}",
                requestDTO.getMethod(), clientName);

        PaymentMethodDetailsDTO paymentMethodDetailsDTO = new PaymentMethodDetailsDTO();

        // client with given payment method doesnt exist
        if (foundClient != null) {
            String clientId = foundClient.getClientId();
            if (!requestDTO.getMethod().equals("paypal")) {
                 clientId = aes.decrypt(foundClient.getClientId());
            }
            ClientPaymentMethodDTO clientDTO = new ClientPaymentMethodDTO(clientId,
                    modelMapper.map(foundClient.getPaymentMethod(), PaymentMethodDTO.class));

            paymentMethodDetailsDTO.setClientPaymentMethodDTO(clientDTO);
        }

        // if payment method is bank, return all banks from the system as well
        if (requestDTO.getMethod().equals("bank")) {
            List<PaymentMethod> banks = clientService.findPaymentMethodByMethod(requestDTO.getMethod());
            paymentMethodDetailsDTO.setPaymentMethods(banks);
        }

        logger.info("Payment method is successfully returned.");

        return ResponseEntity.ok(paymentMethodDetailsDTO);
    }
}
