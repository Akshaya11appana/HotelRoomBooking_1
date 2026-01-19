package com.ey.dto.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PricingQuoteResponse {
  private Long roomTypeId;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private Integer nights;
  private Integer guests;
  private String currency;

  private List<PricingNight> nightly;
  private BigDecimal subtotal;
  private BigDecimal discount;
  private String promoApplied;     // description of promo used (if any)
  private List<TaxLine> taxes;
  private BigDecimal taxTotal;
  private BigDecimal total;

  public Long getRoomTypeId() { return roomTypeId; }
  public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }

  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

  public Integer getNights() { return nights; }
  public void setNights(Integer nights) { this.nights = nights; }

  public Integer getGuests() { return guests; }
  public void setGuests(Integer guests) { this.guests = guests; }

  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }

  public List<PricingNight> getNightly() { return nightly; }
  public void setNightly(List<PricingNight> nightly) { this.nightly = nightly; }

  public BigDecimal getSubtotal() { return subtotal; }
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

  public BigDecimal getDiscount() { return discount; }
  public void setDiscount(BigDecimal discount) { this.discount = discount; }

  public String getPromoApplied() { return promoApplied; }
  public void setPromoApplied(String promoApplied) { this.promoApplied = promoApplied; }

  public List<TaxLine> getTaxes() { return taxes; }
  public void setTaxes(List<TaxLine> taxes) { this.taxes = taxes; }

  public BigDecimal getTaxTotal() { return taxTotal; }
  public void setTaxTotal(BigDecimal taxTotal) { this.taxTotal = taxTotal; }

  public BigDecimal getTotal() { return total; }
  public void setTotal(BigDecimal total) { this.total = total; }
}
