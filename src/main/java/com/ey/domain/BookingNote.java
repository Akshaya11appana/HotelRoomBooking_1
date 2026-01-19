package com.ey.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_notes")
public class BookingNote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  @Column(name = "note", nullable = false, columnDefinition = "TEXT")
  private String note;

  @Column(length = 100)
  private String author;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Booking getBooking() { return booking; }
  public void setBooking(Booking booking) { this.booking = booking; }

  public String getNote() { return note; }
  public void setNote(String note) { this.note = note; }

  public String getAuthor() { return author; }
  public void setAuthor(String author) { this.author = author; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

