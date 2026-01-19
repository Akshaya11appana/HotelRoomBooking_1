package com.ey.dto.booking;

import java.math.BigDecimal;

public class CancelResponse {
  private String message;
  private BigDecimal refundAmount;

  public CancelResponse() { }
  public CancelResponse(String message, BigDecimal refundAmount) {
    this.message = message; this.refundAmount = refundAmount;
  }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
  public BigDecimal getRefundAmount() { return refundAmount; }
  public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }
}
