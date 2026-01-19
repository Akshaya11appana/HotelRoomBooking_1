package com.ey.web;

import com.ey.dto.availability.AvailabilityDtos.Calendar;
import com.ey.dto.availability.AvailabilityDtos.HoldRequest;
import com.ey.dto.availability.AvailabilityDtos.HoldResponse;
import com.ey.dto.availability.AvailabilityDtos.SearchItem;
import com.ey.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AvailabilityController {

  private final AvailabilityService svc;

  public AvailabilityController(AvailabilityService svc) {
    this.svc = svc;
  }

  @GetMapping("/search/availability")
  public List<SearchItem> search(@RequestParam Long roomTypeId,
                                 @RequestParam String from,
                                 @RequestParam String to) {
    LocalDate f = LocalDate.parse(from);
    LocalDate t = LocalDate.parse(to);
    return svc.search(roomTypeId, f, t);
  }

  @GetMapping("/availability/calendar")
  public Calendar calendar(@RequestParam Long roomTypeId,
                           @RequestParam String from,
                           @RequestParam String to) {
    LocalDate f = LocalDate.parse(from);
    LocalDate t = LocalDate.parse(to);
    return svc.calendar(roomTypeId, f, t);
  }

  @PostMapping("/availability/hold")
  public ResponseEntity<HoldResponse> hold(@RequestBody HoldRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(svc.createHold(req));
  }

  @DeleteMapping("/availability/hold/{holdId}")
  public ResponseEntity<Void> release(@PathVariable("holdId") String holdId) {
    svc.releaseHold(holdId);
    return ResponseEntity.noContent().build();
  }
}
