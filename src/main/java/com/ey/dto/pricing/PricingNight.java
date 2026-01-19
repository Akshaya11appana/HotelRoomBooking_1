package com.ey.dto.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PricingNight {
  private LocalDate date;
  private BigDecimal unitPrice;

  public PricingNight() {}
  public PricingNight(LocalDate date, BigDecimal unitPrice) {
    this.date = date;
    this.unitPrice = unitPrice;
  }

  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }

  public BigDecimal getUnitPrice() { return unitPrice; }
  public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
