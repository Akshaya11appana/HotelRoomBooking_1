package com.ey.repo;

import com.ey.domain.AvailabilityHold;
import com.ey.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvailabilityHoldRepo extends JpaRepository<AvailabilityHold, Long> {
  Optional<AvailabilityHold> findByHoldCode(String holdCode);
  void deleteByExpiresAtBefore(LocalDateTime now);  // changed
  List<AvailabilityHold> findByRoomTypeAndExpiresAtAfter(RoomType roomType, LocalDateTime now); // changed
}
