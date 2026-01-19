package com.ey.repo;

import com.ey.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomTypeRepo extends JpaRepository<RoomType, Long> {
  Optional<RoomType> findByCode(String code);
  List<RoomType> findByArchivedFalse();
}
