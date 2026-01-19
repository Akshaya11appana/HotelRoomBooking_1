package com.ey.dto.booking;

import com.ey.domain.Booking;
import com.ey.domain.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {
  private Long id;
  private String bookingCode;
  private Long roomTypeId;
  private String roomTypeCode;
  private String roomTypeName;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private Integer guests;
  private BookingStatus status;
  private String currency;
  private BigDecimal subtotal;
  private BigDecimal discount;
  private BigDecimal taxTotal;
  private BigDecimal totalAmount;
  private String userEmail;
  private String holdCode;
  private LocalDateTime createdAt;

  public static BookingResponse from(Booking b) {
    BookingResponse r = new BookingResponse();
    r.id = b.getId();
    r.bookingCode = b.getBookingCode();
    r.roomTypeId = (b.getRoomType() != null ? b.getRoomType().getId() : null);
    r.roomTypeCode = (b.getRoomType() != null ? b.getRoomType().getCode() : null);
    r.roomTypeName = (b.getRoomType() != null ? b.getRoomType().getName() : null);
    r.checkIn = b.getCheckIn();
    r.checkOut = b.getCheckOut();
    r.guests = b.getGuests();
    r.status = b.getStatus();
    r.currency = b.getCurrency();
    r.subtotal = b.getSubtotal();
    r.discount = b.getDiscount();
    r.taxTotal = b.getTaxTotal();
    r.totalAmount = b.getTotalAmount();
    r.userEmail = b.getUserEmail();
    r.holdCode = b.getHoldCode();
    r.createdAt = b.getCreatedAt();
    return r;
  }

  // Getters & Setters (generate all)
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getBookingCode() { return bookingCode; }
  public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }
  public Long getRoomTypeId() { return roomTypeId; }
  public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }
  public String getRoomTypeCode() { return roomTypeCode; }
  public void setRoomTypeCode(String roomTypeCode) { this.roomTypeCode = roomTypeCode; }
  public String getRoomTypeName() { return roomTypeName; }
  public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }
  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
  public Integer getGuests() { return guests; }
  public void setGuests(Integer guests) { this.guests = guests; }
  public BookingStatus getStatus() { return status; }
  public void setStatus(BookingStatus status) { this.status = status; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
  public BigDecimal getSubtotal() { return subtotal; }
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
  public BigDecimal getDiscount() { return discount; }
  public void setDiscount(BigDecimal discount) { this.discount = discount; }
  public BigDecimal getTaxTotal() { return taxTotal; }
  public void setTaxTotal(BigDecimal taxTotal) { this.taxTotal = taxTotal; }
  public BigDecimal getTotalAmount() { return totalAmount; }
  public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
  public String getUserEmail() { return userEmail; }
  public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
  public String getHoldCode() { return holdCode; }
  public void setHoldCode(String holdCode) { this.holdCode = holdCode; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
