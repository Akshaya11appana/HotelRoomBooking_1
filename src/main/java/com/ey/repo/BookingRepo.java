package com.ey.repo;

import com.ey.domain.Booking;
import com.ey.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {
  Optional<Booking> findByBookingCode(String bookingCode);

  List<Booking> findByUserEmailOrderByCreatedAtDesc(String userEmail);

  List<Booking> findByStatusOrderByCreatedAtDesc(BookingStatus status);

  List<Booking> findByRoomType_IdAndCheckInGreaterThanEqualAndCheckOutLessThanEqualOrderByCreatedAtDesc(
      Long roomTypeId, LocalDate from, LocalDate to);
}
