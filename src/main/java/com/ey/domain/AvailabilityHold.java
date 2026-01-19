package com.ey.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime; 

@Entity
@Table(name = "availability_holds")
public class AvailabilityHold {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "hold_code", unique = true, nullable = false, length = 50)
  private String holdCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_type_id", nullable = false)
  private RoomType roomType;

  @Column(name = "check_in", nullable = false)
  private LocalDate checkIn;

  @Column(name = "check_out", nullable = false)
  private LocalDate checkOut;

  @Column(nullable = false)
  private Integer guests;

  @Column(name = "expires_at", nullable = false)
  private LocalDateTime expiresAt;  // changed

  @Column(name = "user_email")
  private String userEmail;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getHoldCode() { return holdCode; }
  public void setHoldCode(String holdCode) { this.holdCode = holdCode; }
  public RoomType getRoomType() { return roomType; }
  public void setRoomType(RoomType roomType) { this.roomType = roomType; }
  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
  public Integer getGuests() { return guests; }
  public void setGuests(Integer guests) { this.guests = guests; }
  public LocalDateTime getExpiresAt() { return expiresAt; }
  public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
  public String getUserEmail() { return userEmail; }
  public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
