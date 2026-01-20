
package com.ey.controller;

import com.ey.dto.pricing.PricingQuoteRequest;
import com.ey.dto.pricing.PricingQuoteResponse;
import com.ey.service.PricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pricing")
public class PricingController {

  private final PricingService pricingService;

  public PricingController(PricingService pricingService) {
    this.pricingService = pricingService;
  }

  @PostMapping("/quote")
  public ResponseEntity<PricingQuoteResponse> quote(@RequestBody PricingQuoteRequest req) {
    return ResponseEntity.ok(pricingService.quote(req));
  }
}
