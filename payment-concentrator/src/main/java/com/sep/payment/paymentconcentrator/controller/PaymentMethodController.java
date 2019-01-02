package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.ClientDTO;
import com.sep.payment.paymentconcentrator.domain.dto.NewMethodDTO;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentMethodDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/pc")
public class PaymentMethodController {

    @Autowired
    private ClientService clientService;

    private ModelMapper modelMapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);

    @PostMapping(value = "/payment-methods")
    public ResponseEntity<Set<PaymentMethodDTO>> checkPaymentMethods(@RequestBody @Valid ClientDTO clientDTO) throws UnsupportedEncodingException {
        logger.info("Request - return all possible payment methods.");

        String clientName = Utility.readToken(clientDTO.getId());
        List<Client> clients = clientService.getAllMethods(clientName);

        if (clients == null) {
            logger.info("This client doesn't have any registered payment methods.");
            // todo: return here
            ResponseEntity.notFound();
        }
        Set<PaymentMethodDTO> paymentMethodDTOS = new HashSet<>();
        Objects.requireNonNull(clients).forEach(client -> paymentMethodDTOS.add(modelMapper.map(client.getPaymentMethod(), PaymentMethodDTO.class)));

        logger.info("This client has registered payment methods.");

        //kreirati token koji ce dalje biti za autentifikaciju
        return ResponseEntity.ok().body(paymentMethodDTOS);
    }

    @PostMapping(value = "/new-method")
    public ResponseEntity<Client> enableNewPaymentMethod(@RequestBody @Valid NewMethodDTO newMethodDTO) {
        logger.info("Request - enable new payment method, that exists in the system.");

        Client client = clientService.addNewMethod(newMethodDTO.getClientName(), newMethodDTO.getClientId(), newMethodDTO.getClientPassword(),
                newMethodDTO.getMethod());

        logger.info("Payment method is successfully enabled.");

        return ResponseEntity.ok().body(client);
    }
}
