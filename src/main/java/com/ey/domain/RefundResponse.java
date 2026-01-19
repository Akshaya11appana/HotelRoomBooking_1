package com.ey.domain;

import com.ey.domain.Refund;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RefundResponse {

    private Long id;
    private BigDecimal refundAmount;
    private String currency;
    private String reason;
    private LocalDateTime createdAt;

    public static RefundResponse from(Refund r) {
        RefundResponse res = new RefundResponse();
        res.id = r.getId();
        res.refundAmount = r.getRefundAmount();
        res.currency = r.getCurrency();
        res.reason = r.getReason();
        res.createdAt = r.getCreatedAt();
        return res;
    }

    public Long getId() { return id; }
    public BigDecimal getRefundAmount() { return refundAmount; }
    public String getCurrency() { return currency; }
    public String getReason() { return reason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
