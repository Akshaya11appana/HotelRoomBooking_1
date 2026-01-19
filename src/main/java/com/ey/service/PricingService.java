package com.ey.service;

import com.ey.domain.Availability;
import com.ey.domain.RoomType;
import com.ey.dto.pricing.PricingNight;
import com.ey.dto.pricing.PricingQuoteRequest;
import com.ey.dto.pricing.PricingQuoteResponse;
import com.ey.dto.pricing.TaxLine;
import com.ey.repo.AvailabilityRepo;
import com.ey.repo.RoomTypeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PricingService {

  private final AvailabilityRepo availabilityRepo;
  private final RoomTypeRepo roomTypeRepo;

  @Value("${pricing.taxPercent:12}")
  private BigDecimal taxPercent;

  @Value("${pricing.currency:INR}")
  private String currency;

  public PricingService(AvailabilityRepo availabilityRepo, RoomTypeRepo roomTypeRepo) {
    this.availabilityRepo = availabilityRepo;
    this.roomTypeRepo = roomTypeRepo;
  }

  @Transactional(readOnly = true)
  public PricingQuoteResponse quote(PricingQuoteRequest req) {
    if (req.getRoomTypeId() == null) throw new RuntimeException("roomTypeId is required");
    if (req.getCheckIn() == null || req.getCheckOut() == null) throw new RuntimeException("Dates are required");
    if (!req.getCheckIn().isBefore(req.getCheckOut())) throw new RuntimeException("checkIn must be before checkOut");
    if (req.getGuests() == null || req.getGuests() <= 0) throw new RuntimeException("guests must be > 0");

    RoomType rt = roomTypeRepo.findById(req.getRoomTypeId())
        .orElseThrow(() -> new RuntimeException("Room type not found"));

    if (req.getGuests() > (rt.getMaxOccupancy() != null ? rt.getMaxOccupancy() : Integer.MAX_VALUE)) {
      throw new RuntimeException("Guests exceed max occupancy");
    }

    List<PricingNight> nights = new ArrayList<>();
    LocalDate d = req.getCheckIn();
    while (d.isBefore(req.getCheckOut())) {
      var cells = availabilityRepo.findByRoomType_IdAndDateBetween(rt.getId(), d, d);
      BigDecimal unit = rt.getBasePrice(); // default
      if (!cells.isEmpty() && cells.get(0).getPrice() != null) {
        unit = cells.get(0).getPrice();
      }
      nights.add(new PricingNight(d, unit.setScale(2, RoundingMode.HALF_UP)));
      d = d.plusDays(1);
    }

    BigDecimal subtotal = nights.stream()
        .map(PricingNight::getUnitPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.HALF_UP);

    String promoApplied = null;
    BigDecimal discount = BigDecimal.ZERO;
    if (req.getPromoCode() != null && !req.getPromoCode().isBlank()) {
      var result = applyPromo(req.getPromoCode().trim().toUpperCase(), nights, subtotal);
      discount = result.discount();
      promoApplied = result.description();
      if (discount.compareTo(subtotal) > 0) discount = subtotal; // safety
      discount = discount.setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal taxable = subtotal.subtract(discount);
    if (taxable.compareTo(BigDecimal.ZERO) < 0) taxable = BigDecimal.ZERO;
    BigDecimal taxAmount = taxable.multiply(taxPercent)
        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

    List<TaxLine> taxes = List.of(new TaxLine("Tax " + taxPercent + "%", taxAmount));

    BigDecimal total = taxable.add(taxAmount).setScale(2, RoundingMode.HALF_UP);

    PricingQuoteResponse res = new PricingQuoteResponse();
    res.setRoomTypeId(rt.getId());
    res.setCheckIn(req.getCheckIn());
    res.setCheckOut(req.getCheckOut());
    res.setNights(nights.size());
    res.setGuests(req.getGuests());
    res.setCurrency(currency);
    res.setNightly(nights);
    res.setSubtotal(subtotal);
    res.setDiscount(discount);
    res.setPromoApplied(promoApplied);
    res.setTaxes(taxes);
    res.setTaxTotal(taxAmount);
    res.setTotal(total);
    return res;
  }

  
  private PromoResult applyPromo(String code, List<PricingNight> nights, BigDecimal subtotal) {
    switch (code) {
      case "WELCOME10": {
        BigDecimal d = subtotal.multiply(new BigDecimal("0.10"));
        return new PromoResult(d, "WELCOME10 (10% off)");
      }
      case "FLAT500": {
        return new PromoResult(new BigDecimal("500.00"), "FLAT500 (₹500 off)");
      }
      case "STAY3PAY2": {
        BigDecimal discount = BigDecimal.ZERO;
        int n = nights.size();
        for (int i = 0; i + 2 < n; i += 3) {
          BigDecimal p1 = nights.get(i).getUnitPrice();
          BigDecimal p2 = nights.get(i + 1).getUnitPrice();
          BigDecimal p3 = nights.get(i + 2).getUnitPrice();
          BigDecimal cheapest = p1.min(p2).min(p3);
          discount = discount.add(cheapest);
        }
        return new PromoResult(discount, "STAY3PAY2 (every 3rd night free)");
      }
      default:
        return new PromoResult(BigDecimal.ZERO, "No valid promo applied");
    }
  }

  private record PromoResult(BigDecimal discount, String description) {}
}

