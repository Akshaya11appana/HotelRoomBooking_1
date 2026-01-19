package com.ey.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ey.domain.Booking;
import com.ey.domain.BookingNote;
import com.ey.domain.BookingStatus;
import com.ey.domain.RoomType;
import com.ey.domain.User;
import com.ey.dto.booking.BookingCreateRequest;
import com.ey.dto.booking.BookingNoteRequest;
import com.ey.dto.booking.BookingResponse;
import com.ey.dto.booking.CancelResponse;
import com.ey.dto.pricing.PricingQuoteRequest;
import com.ey.dto.pricing.PricingQuoteResponse;
import com.ey.repo.AvailabilityHoldRepo;
import com.ey.repo.BookingNoteRepo;
import com.ey.repo.BookingRepo;
import com.ey.repo.RoomTypeRepo;
import com.ey.repo.UserRepo;
import com.ey.security.CurrentUser;

@Service
public class BookingService {

  private final BookingRepo bookingRepo;
  private final BookingNoteRepo noteRepo;
  private final RoomTypeRepo roomTypeRepo;
  private final UserRepo userRepo;
  private final AvailabilityHoldRepo holdRepo;
  private final PricingService pricingService;
  private final CurrentUser currentUser;

  public BookingService(BookingRepo bookingRepo,
                        BookingNoteRepo noteRepo,
                        RoomTypeRepo roomTypeRepo,
                        UserRepo userRepo,
                        AvailabilityHoldRepo holdRepo,
                        PricingService pricingService,
                        CurrentUser currentUser) {
    this.bookingRepo = bookingRepo;
    this.noteRepo = noteRepo;
    this.roomTypeRepo = roomTypeRepo;
    this.userRepo = userRepo;
    this.holdRepo = holdRepo;
    this.pricingService = pricingService;
    this.currentUser = currentUser;
  }

  private void ensureOwnerOrAdmin(Booking b) {
    String me = currentUser.email();
    if (currentUser.isAdmin() || (me != null && me.equalsIgnoreCase(b.getUserEmail()))) return;
    throw new RuntimeException("Forbidden");
  }

  @Transactional
  public BookingResponse create(BookingCreateRequest req) {
    if (req.getCheckIn() == null || req.getCheckOut() == null || !req.getCheckIn().isBefore(req.getCheckOut())) {
      throw new RuntimeException("Invalid dates");
    }
    RoomType rt = roomTypeRepo.findById(req.getRoomTypeId())
        .orElseThrow(() -> new RuntimeException("Room type not found"));

  
    if (req.getHoldCode() != null && !req.getHoldCode().isBlank()) {
      var hold = holdRepo.findByHoldCode(req.getHoldCode())
          .orElseThrow(() -> new RuntimeException("Hold not found"));
      if (!hold.getRoomType().getId().equals(rt.getId())) throw new RuntimeException("Hold/roomType mismatch");
      if (!hold.getCheckIn().equals(req.getCheckIn()) || !hold.getCheckOut().equals(req.getCheckOut()))
        throw new RuntimeException("Hold dates mismatch");
      if (hold.getExpiresAt().isBefore(java.time.LocalDateTime.now()))
        throw new RuntimeException("Hold expired");
    }

    PricingQuoteRequest pq = new PricingQuoteRequest();
    pq.setRoomTypeId(rt.getId());
    pq.setCheckIn(req.getCheckIn());
    pq.setCheckOut(req.getCheckOut());
    pq.setGuests(req.getGuests());
    pq.setPromoCode(req.getPromoCode());
    PricingQuoteResponse price = pricingService.quote(pq);

    String me = currentUser.email();
    User owner = null;
    if (me != null) {
      owner = userRepo.findByEmail(me).orElse(null);
    }

    Booking b = new Booking();
    b.setBookingCode("BKG-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
    b.setUser(owner);
    b.setUserEmail(me);
    b.setRoomType(rt);
    b.setCheckIn(req.getCheckIn());
    b.setCheckOut(req.getCheckOut());
    b.setGuests(req.getGuests());
    b.setStatus(BookingStatus.CONFIRMED); 
    b.setCurrency(price.getCurrency());
    b.setSubtotal(price.getSubtotal());
    b.setDiscount(price.getDiscount() != null ? price.getDiscount() : BigDecimal.ZERO);
    b.setTaxTotal(price.getTaxTotal() != null ? price.getTaxTotal() : BigDecimal.ZERO);
    b.setTotalAmount(price.getTotal());
    b.setHoldCode(req.getHoldCode());
    b.setCreatedAt(LocalDateTime.now());

    bookingRepo.save(b);

    
    if (req.getHoldCode() != null && !req.getHoldCode().isBlank()) {
      holdRepo.findByHoldCode(req.getHoldCode()).ifPresent(holdRepo::delete);
    }

    return BookingResponse.from(b);
  }

  @Transactional(readOnly = true)
  public BookingResponse get(Long id) {
    Booking b = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    ensureOwnerOrAdmin(b);
    return BookingResponse.from(b);
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> listMine() {
    String me = currentUser.email();
    if (me == null) throw new RuntimeException("Unauthorized");
    return bookingRepo.findByUserEmailOrderByCreatedAtDesc(me).stream().map(BookingResponse::from).toList();
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> listAll() {
    if (!currentUser.isAdmin() && !currentUser.isStaff()) throw new RuntimeException("Forbidden");
    return bookingRepo.findAll().stream().sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
        .map(BookingResponse::from).toList();
  }

  @Transactional
  public CancelResponse cancel(Long id) {
    Booking b = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    ensureOwnerOrAdmin(b);
    if (b.getStatus() == BookingStatus.CANCELED) return new CancelResponse("Already canceled", BigDecimal.ZERO);
    BigDecimal refund = BigDecimal.ZERO;
   
    if (LocalDate.now().isBefore(b.getCheckIn())) {
      refund = b.getTotalAmount();
    }
    b.setStatus(BookingStatus.CANCELED);
    bookingRepo.save(b);
    return new CancelResponse("Booking canceled", refund);
  }

  @Transactional
  public BookingResponse checkIn(Long id) {
    if (!currentUser.isAdmin() && !currentUser.isStaff()) throw new RuntimeException("Forbidden");
    Booking b = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    if (b.getStatus() != BookingStatus.CONFIRMED) throw new RuntimeException("Invalid state");
    b.setStatus(BookingStatus.CHECKED_IN);
    bookingRepo.save(b);
    return BookingResponse.from(b);
  }

  @Transactional
  public BookingResponse checkOut(Long id) {
    if (!currentUser.isAdmin() && !currentUser.isStaff()) throw new RuntimeException("Forbidden");
    Booking b = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    if (b.getStatus() != BookingStatus.CHECKED_IN) throw new RuntimeException("Invalid state");
    b.setStatus(BookingStatus.CHECKED_OUT);
    bookingRepo.save(b);
    return BookingResponse.from(b);
  }

  @Transactional
  public Map<String, Object> addNote(Long id, BookingNoteRequest req) {
    Booking b = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    ensureOwnerOrAdmin(b);
    BookingNote n = new BookingNote();
    n.setBooking(b);
    n.setNote(req.getNote());
    n.setAuthor(currentUser.email());
    n.setCreatedAt(LocalDateTime.now());
    noteRepo.save(n);
    return Map.of("message", "Note added");
  }



@Transactional(readOnly = true)
public List<Map<String, Object>> listNotes(Long id) {

    Booking b = bookingRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    ensureOwnerOrAdmin(b);

    return noteRepo.findByBooking_IdOrderByCreatedAtAsc(id)
            .stream()
            .map(n -> Map.<String, Object>of(
                    "id", n.getId(),
                    "note", n.getNote(),
                    "author", n.getAuthor(),
                    "createdAt", n.getCreatedAt()
            ))
            .toList();
}

}

