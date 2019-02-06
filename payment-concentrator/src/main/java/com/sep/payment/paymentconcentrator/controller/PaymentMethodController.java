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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/pc")
public class PaymentMethodController {

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
        }

        Set<PaymentMethodDTO> paymentMethodDTOS = new HashSet<>();

        Objects.requireNonNull(clients).forEach(client -> paymentMethodDTOS.add(modelMapper.map(client.getPaymentMethod(),
                PaymentMethodDTO.class)));
        ResponseEntity<Object> res = restTemplate.postForEntity("http://localhost:9100/auth", clientDTO, Object.class);
        logger.info("This client has registered payment methods.");

        PaymentMethodsAndTokenDTO response = PaymentMethodsAndTokenDTO.builder().paymentMethodDTOS(paymentMethodDTOS).
                amount(Double.parseDouble(token.split("-")[3]))
                .token(res.getHeaders().get("Authorization").get(0)).build();

        return ResponseEntity.ok(response);
    }

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

    @PostMapping(value = "/payment-method-details")
    public ResponseEntity getPaymentMethodDetails(@RequestBody PaymentMethodDetailsRequestDTO requestDTO)
            throws UnsupportedEncodingException {
        logger.info("Request- get payment method details.");
        String token = Utility.readToken(requestDTO.getClientId());
        String clientName = token.split("-")[2];
        Client foundClient = clientService.findByClientMethod(clientName, requestDTO.getMethodName());
        logger.info("Get payment method details for the {}, for the client: {}",
                requestDTO.getMethod(), clientName);

        PaymentMethodDetailsDTO paymentMethodDetailsDTO = new PaymentMethodDetailsDTO();

        if (foundClient != null) {
            String clientId = aes.decrypt(foundClient.getClientId());
            ClientPaymentMethodDTO clientDTO = new ClientPaymentMethodDTO(clientId,
                    modelMapper.map(foundClient.getPaymentMethod(), PaymentMethodDTO.class));

            paymentMethodDetailsDTO.setClientPaymentMethodDTO(clientDTO);
        }

        if (requestDTO.getMethod().equals("bank")) {
            List<PaymentMethod> banks = clientService.findPaymentMethodByMethod(requestDTO.getMethod());
            paymentMethodDetailsDTO.setPaymentMethods(banks);
        }

        logger.info("Payment method is successfully returned.");

        return ResponseEntity.ok(paymentMethodDetailsDTO);
    }

    @PostMapping(value = "/toj")
    public void p(@RequestBody String t) {
        String enc = aes.encrypt(t);
        System.out.println(enc);
        String dec = aes.decrypt(enc);
        System.out.println(dec);
    }
}
