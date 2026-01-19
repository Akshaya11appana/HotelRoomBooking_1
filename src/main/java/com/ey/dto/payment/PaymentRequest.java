package com.ey.dto.payment;

public class PaymentRequest {

    private String method;
    private String providerRef;

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getProviderRef() { return providerRef; }
    public void setProviderRef(String providerRef) { this.providerRef = providerRef; }
}
