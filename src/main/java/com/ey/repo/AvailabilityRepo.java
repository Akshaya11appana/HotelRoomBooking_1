package com.ey.repo;

import com.ey.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepo extends JpaRepository<Availability, Long> {
  List<Availability> findByRoomType_IdAndDateBetween(Long roomTypeId, LocalDate from, LocalDate to);
}
