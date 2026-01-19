package com.ey.web;

import com.ey.dto.room.RoomTypeCreate;
import com.ey.dto.room.RoomTypeDto;
import com.ey.dto.room.RoomTypeUpdate;
import com.ey.service.RoomTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RoomTypeController {

  private final RoomTypeService svc;

  public RoomTypeController(RoomTypeService svc) {
    this.svc = svc;
  }

  
  @GetMapping("/room-types")
  public List<RoomTypeDto> list() {
    return svc.list();
  }

 
  @GetMapping("/room-types/{id}")
  public RoomTypeDto get(@PathVariable Long id) {
    return svc.get(id);
  }

 
  @PostMapping("/room-types")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Map<String, Object>> create(@RequestBody RoomTypeCreate r) {
    return ResponseEntity.status(HttpStatus.CREATED).body(svc.create(r));
  }

 
  @PutMapping("/room-types/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Map<String, String> update(@PathVariable Long id, @RequestBody RoomTypeUpdate r) {
    svc.update(id, r);
    return Map.of("message", "Room type updated");
  }

  @DeleteMapping("/room-types/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Map<String, String> archive(@PathVariable Long id) {
    svc.archive(id);
    return Map.of("message", "Room type archived");
  }

  @GetMapping("/amenities")
  public Map<String, List<String>> amenities() {
    return Map.of("items", svc.amenities());
  }
}
