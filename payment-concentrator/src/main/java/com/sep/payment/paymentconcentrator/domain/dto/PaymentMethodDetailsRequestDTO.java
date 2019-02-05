package com.sep.payment.paymentconcentrator.domain.dto;

public class PaymentMethodDetailsRequestDTO {

    private String clientId;
    private String method;
    private String methodName;

    public PaymentMethodDetailsRequestDTO() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
