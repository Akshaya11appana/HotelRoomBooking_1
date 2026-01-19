package com.ey.service;

import com.ey.domain.RoomType;
import com.ey.dto.room.RoomTypeCreate;
import com.ey.dto.room.RoomTypeDto;
import com.ey.dto.room.RoomTypeUpdate;
import com.ey.repo.RoomTypeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RoomTypeService {

  private final RoomTypeRepo repo;

  public RoomTypeService(RoomTypeRepo repo) {
    this.repo = repo;
  }

  @Transactional(readOnly = true)
  public List<RoomTypeDto> list() {
    return repo.findByArchivedFalse().stream().map(RoomTypeDto::from).toList();
  }

  @Transactional(readOnly = true)
  public RoomTypeDto get(Long id) {
    RoomType rt = repo.findById(id).orElseThrow(() -> new RuntimeException("Room type not found"));
    if (rt.isArchived()) throw new RuntimeException("Room type archived");
    return RoomTypeDto.from(rt);
  }

  @Transactional
  public Map<String,Object> create(RoomTypeCreate r) {
    repo.findByCode(r.getCode()).ifPresent(x -> { throw new RuntimeException("Code already exists"); });

    RoomType rt = new RoomType();
    rt.setCode(r.getCode());
    rt.setName(r.getName());
    rt.setDescription(r.getDescription());
    rt.setBasePrice(r.getBasePrice());
    rt.setMaxOccupancy(r.getMaxOccupancy());
    if (r.getAmenities() != null) rt.setAmenities(r.getAmenities());

    repo.save(rt);
    return Map.of("id", rt.getId(), "message", "Room type created");
  }

  @Transactional
  public void update(Long id, RoomTypeUpdate r) {
    RoomType rt = repo.findById(id).orElseThrow(() -> new RuntimeException("Room type not found"));
    if (r.getName() != null) rt.setName(r.getName());
    if (r.getDescription() != null) rt.setDescription(r.getDescription());
    if (r.getBasePrice() != null) rt.setBasePrice(r.getBasePrice());
    if (r.getMaxOccupancy() != null) rt.setMaxOccupancy(r.getMaxOccupancy());
    if (r.getAmenities() != null) rt.setAmenities(r.getAmenities());
    repo.save(rt);
  }

  @Transactional
  public void archive(Long id) {
    RoomType rt = repo.findById(id).orElseThrow(() -> new RuntimeException("Room type not found"));
    rt.setArchived(true);
    repo.save(rt);
  }

  public List<String> amenities() {
    return List.of("WiFi", "AC", "Breakfast", "TV", "Gym", "Pool");
  }
}
