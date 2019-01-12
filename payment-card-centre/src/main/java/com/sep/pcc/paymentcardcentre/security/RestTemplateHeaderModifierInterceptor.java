package com.sep.pcc.paymentcardcentre.security;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        String token = RequestContext.getContext().getToken();
        request.getHeaders().add(RequestContext.REQUEST_HEADER_NAME, "Bearer " + token);
        return execution.execute(request, body);

    }
}