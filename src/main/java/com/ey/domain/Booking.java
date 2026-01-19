package com.ey.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "booking_code", nullable = false, unique = true, length = 50)
  private String bookingCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;                       

  @Column(name = "user_email")
  private String userEmail;                
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_type_id", nullable = false)
  private RoomType roomType;

  @Column(name = "check_in", nullable = false)
  private LocalDate checkIn;

  @Column(name = "check_out", nullable = false)
  private LocalDate checkOut;

  @Column(nullable = false)
  private Integer guests;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private BookingStatus status;

  @Column(nullable = false, length = 10)
  private String currency;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal subtotal;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal discount;

  @Column(name = "tax_total", nullable = false, precision = 12, scale = 2)
  private BigDecimal taxTotal;

  @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "hold_code")
  private String holdCode;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getBookingCode() { return bookingCode; }
  public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

  public String getUserEmail() { return userEmail; }
  public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

  public RoomType getRoomType() { return roomType; }
  public void setRoomType(RoomType roomType) { this.roomType = roomType; }

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

  public String getHoldCode() { return holdCode; }
  public void setHoldCode(String holdCode) { this.holdCode = holdCode; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
