package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.ClientDTO;
import com.sep.payment.paymentconcentrator.domain.dto.NewMethodDTO;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentMethodDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
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
import java.util.Set;
@RestController
@RequestMapping(value = "/pc")
public class PaymentMethodController {

    @Autowired
    private ClientService clientService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/payment-methods")
    public ResponseEntity<Set<PaymentMethodDTO>> checkPaymentMethods(@RequestBody @Valid ClientDTO clientDTO) throws UnsupportedEncodingException {
        String clientName = Utility.readToken(clientDTO.getId());
        List<Client> clients = clientService.getAllMethods(clientName);
        if(clients == null){
            ResponseEntity.notFound();
        }
        Set<PaymentMethodDTO> paymentMethodDTOS = new HashSet<>();
        clients.forEach(client -> paymentMethodDTOS.add(modelMapper.map(client.getPaymentMethod(), PaymentMethodDTO.class)));

        //kreirati token koji ce dalje biti za autentifikaciju
        return ResponseEntity.ok().body(paymentMethodDTOS);
    }

    @PostMapping(value = "/new-method")
    public ResponseEntity<Client> enableNewPaymentMethod(@RequestBody @Valid NewMethodDTO newMethodDTO) {
        Client client = clientService.addNewMethod(newMethodDTO.getClientName(), newMethodDTO.getClientId(), newMethodDTO.getClientPassword(),
                newMethodDTO.getMethod());
        return ResponseEntity.ok().body(client);
    }
}
