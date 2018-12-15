package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.ClientDTO;
import com.sep.payment.paymentconcentrator.domain.dto.NewMethodDTO;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentMethodDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.security.RequestContext;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/pc")
public class PaymentMethodController {

    private final ClientService clientService;
    private final RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    public PaymentMethodController(ClientService clientService, RestTemplate restTemplate) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/payment-methods")
    public ResponseEntity<Set<PaymentMethodDTO>> checkPaymentMethods(@RequestBody @Valid ClientDTO clientDTO) throws UnsupportedEncodingException {
        String clientName = Utility.readToken(clientDTO.getClientId());
        List<Client> clients = clientService.getAllMethods(clientName);
        if (clients == null) {
            ResponseEntity.notFound();
        }
        Set<PaymentMethodDTO> paymentMethodDTOS = new HashSet<>();
        clients.forEach(client -> paymentMethodDTOS.add(modelMapper.map(client.getPaymentMethod(), PaymentMethodDTO.class)));
        ResponseEntity<Object> res = restTemplate.postForEntity("http://localhost:9100/auth",clientDTO, Object.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add(RequestContext.REQUEST_HEADER_NAME, "Bearer " + res.getHeaders());
        return new ResponseEntity<Set<PaymentMethodDTO>>(paymentMethodDTOS, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/new-method")
    public ResponseEntity<Client> addPaymentMethod(@RequestBody @Valid NewMethodDTO newMethodDTO) {
        Client client = clientService.addNewMethod(newMethodDTO.getClientName(), newMethodDTO.getClientId(), newMethodDTO.getClientPassword(),
                newMethodDTO.getMethod());
        return ResponseEntity.ok().body(client);
    }
}
