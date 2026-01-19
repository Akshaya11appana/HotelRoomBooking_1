package com.ey.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "availability",
       uniqueConstraints = @UniqueConstraint(columnNames = {"room_type_id","date"}))
public class Availability {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_type_id", nullable = false)
  private RoomType roomType;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "available_count", nullable = false)
  private Integer availableCount;

  @Column(precision = 12, scale = 2)
  private BigDecimal price;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public RoomType getRoomType() { return roomType; }
  public void setRoomType(RoomType roomType) { this.roomType = roomType; }

  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }

  public Integer getAvailableCount() { return availableCount; }
  public void setAvailableCount(Integer availableCount) { this.availableCount = availableCount; }

  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
}
