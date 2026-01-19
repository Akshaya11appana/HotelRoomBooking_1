
package com.ey.dto.booking;

import java.time.LocalDate;

public class BookingCreateRequest {
  private Long roomTypeId;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private Integer guests;
  private String promoCode; 
  private String holdCode;  
  public Long getRoomTypeId() { return roomTypeId; }
  public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }
  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
  public Integer getGuests() { return guests; }
  public void setGuests(Integer guests) { this.guests = guests; }
  public String getPromoCode() { return promoCode; }
  public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
  public String getHoldCode() { return holdCode; }
  public void setHoldCode(String holdCode) { this.holdCode = holdCode; }
}
