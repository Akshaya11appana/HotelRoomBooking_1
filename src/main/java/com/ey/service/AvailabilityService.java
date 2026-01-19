package com.ey.service;

import com.ey.domain.Availability;
import com.ey.domain.AvailabilityHold;
import com.ey.domain.RoomType;
import com.ey.dto.availability.AvailabilityDtos.Calendar;
import com.ey.dto.availability.AvailabilityDtos.HoldRequest;
import com.ey.dto.availability.AvailabilityDtos.HoldResponse;
import com.ey.dto.availability.AvailabilityDtos.SearchItem;
import com.ey.repo.AvailabilityHoldRepo;
import com.ey.repo.AvailabilityRepo;
import com.ey.repo.RoomTypeRepo;
import com.ey.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;     // changed
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

  private final AvailabilityRepo availabilityRepo;
  private final AvailabilityHoldRepo holdRepo;
  private final RoomTypeRepo roomTypeRepo;
  private final CurrentUser currentUser;

  public AvailabilityService(AvailabilityRepo availabilityRepo,
                             AvailabilityHoldRepo holdRepo,
                             RoomTypeRepo roomTypeRepo,
                             CurrentUser currentUser) {
    this.availabilityRepo = availabilityRepo;
    this.holdRepo = holdRepo;
    this.roomTypeRepo = roomTypeRepo;
    this.currentUser = currentUser;
  }

  private static List<LocalDate> datesBetweenInclusive(LocalDate from, LocalDate to) {
    List<LocalDate> list = new ArrayList<>();
    for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) list.add(d);
    return list;
  }

  private static boolean overlapsNight(AvailabilityHold h, LocalDate day) {
   
    return !h.getCheckIn().isAfter(day) && h.getCheckOut().isAfter(day);
  }

  @Transactional(readOnly = true)
  public List<SearchItem> search(Long roomTypeId, LocalDate from, LocalDate to) {
    RoomType rt = roomTypeRepo.findById(roomTypeId)
        .orElseThrow(() -> new RuntimeException("Room type not found"));

    Map<LocalDate, Availability> byDate = availabilityRepo
        .findByRoomType_IdAndDateBetween(roomTypeId, from, to)
        .stream()
        .collect(Collectors.toMap(Availability::getDate, a -> a));

    List<AvailabilityHold> activeHolds =
        holdRepo.findByRoomTypeAndExpiresAtAfter(rt, LocalDateTime.now()); 

    List<SearchItem.DateCell> cells = new ArrayList<>();
    for (LocalDate day : datesBetweenInclusive(from, to)) {
      int baseCount = 0;
      Availability a = byDate.get(day);
      if (a != null) baseCount = a.getAvailableCount();

      int holdCount = 0;
      for (AvailabilityHold h : activeHolds) {
        if (overlapsNight(h, day)) holdCount++;
      }

      int finalCount = Math.max(0, baseCount - holdCount);
      cells.add(new SearchItem.DateCell(day, finalCount > 0, finalCount));
    }

    SearchItem.RoomTypeInfo info = new SearchItem.RoomTypeInfo();
    info.setId(rt.getId());
    info.setCode(rt.getCode());
    info.setName(rt.getName());
    info.setMaxOccupancy(rt.getMaxOccupancy());

    SearchItem item = new SearchItem();
    item.setRoomType(info);
    item.setDates(cells);
    item.setBasePrice(rt.getBasePrice());
    return List.of(item);
  }

  @Transactional(readOnly = true)
  public Calendar calendar(Long roomTypeId, LocalDate from, LocalDate to) {
    RoomType rt = roomTypeRepo.findById(roomTypeId)
        .orElseThrow(() -> new RuntimeException("Room type not found"));

    Map<LocalDate, Availability> byDate = availabilityRepo
        .findByRoomType_IdAndDateBetween(roomTypeId, from, to)
        .stream()
        .collect(Collectors.toMap(Availability::getDate, a -> a));

    List<AvailabilityHold> activeHolds =
        holdRepo.findByRoomTypeAndExpiresAtAfter(rt, LocalDateTime.now()); 

    List<Calendar.Row> rows = new ArrayList<>();
    for (LocalDate day : datesBetweenInclusive(from, to)) {
      Availability a = byDate.get(day);
      int baseCount = (a != null ? a.getAvailableCount() : 0);

      int holdCount = 0;
      for (AvailabilityHold h : activeHolds) {
        if (overlapsNight(h, day)) holdCount++;
      }

      int finalCount = Math.max(0, baseCount - holdCount);

      Calendar.Row row = new Calendar.Row();
      row.setDate(day);
      row.setAvailable(finalCount > 0);
      row.setAvailableCount(finalCount);
      BigDecimal price = (a != null && a.getPrice() != null) ? a.getPrice() : rt.getBasePrice();
      row.setPrice(price);
      rows.add(row);
    }

    Calendar cal = new Calendar();
    cal.setRoomTypeId(roomTypeId);
    cal.setDates(rows);
    return cal;
  }

  @Transactional
  public HoldResponse createHold(HoldRequest req) {
    if (req.getCheckIn() == null || req.getCheckOut() == null
        || !req.getCheckIn().isBefore(req.getCheckOut())) {
      throw new RuntimeException("Invalid dates");
    }

    RoomType rt = roomTypeRepo.findById(req.getRoomTypeId())
        .orElseThrow(() -> new RuntimeException("Room type not found"));

    List<AvailabilityHold> activeHolds =
        holdRepo.findByRoomTypeAndExpiresAtAfter(rt, LocalDateTime.now());

    for (LocalDate day = req.getCheckIn(); day.isBefore(req.getCheckOut()); day = day.plusDays(1)) {
      List<Availability> cells = availabilityRepo.findByRoomType_IdAndDateBetween(rt.getId(), day, day);
      int baseCount = cells.isEmpty() ? 0 : cells.get(0).getAvailableCount();

      int holdCount = 0;
      for (AvailabilityHold h : activeHolds) {
        if (overlapsNight(h, day)) holdCount++;
      }

      if ((baseCount - holdCount) <= 0) {
        throw new RuntimeException("Insufficient availability on " + day);
      }
    }

    String code = "HOLD-" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    AvailabilityHold h = new AvailabilityHold();
    h.setHoldCode(code);
    h.setRoomType(rt);
    h.setCheckIn(req.getCheckIn());
    h.setCheckOut(req.getCheckOut());
    h.setGuests(req.getGuests() != null ? req.getGuests() : 1);
    h.setExpiresAt(LocalDateTime.now().plusMinutes(15));  
    h.setUserEmail(currentUser.email());
    holdRepo.save(h);

    return new HoldResponse(code, h.getExpiresAt());
  }

  @Transactional
  public void releaseHold(String holdCode) {
    AvailabilityHold h = holdRepo.findByHoldCode(holdCode)
        .orElseThrow(() -> new RuntimeException("Hold not found"));
    holdRepo.delete(h);
  }

  @Transactional
  public void purgeExpiredHolds() {
    holdRepo.deleteByExpiresAtBefore(LocalDateTime.now());  
  }
}
