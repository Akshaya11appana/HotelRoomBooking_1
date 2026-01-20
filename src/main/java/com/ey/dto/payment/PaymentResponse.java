package com.ey.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ey.domain.Payment;

public class PaymentResponse {

    private Long id;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String method;
    private String providerRef;
    private LocalDateTime createdAt;

    public static PaymentResponse from(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.id = p.getId();
        r.amount = p.getAmount();
        r.currency = p.getCurrency();
        r.status = p.getStatus();
        r.method = p.getMethod();
        r.providerRef = p.getProviderRef();
        r.createdAt = p.getCreatedAt();
        return r;
    }

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public String getMethod() { return method; }
    public String getProviderRef() { return providerRef; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
