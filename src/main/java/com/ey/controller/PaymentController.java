
package com.ey.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.domain.RefundResponse;
import com.ey.dto.payment.PaymentRequest;
import com.ey.dto.payment.PaymentResponse;
import com.ey.dto.payment.RefundRequest;
import com.ey.service.PaymentService;

@RestController
@RequestMapping("/api/v1/bookings/{bookingId}/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> pay(@PathVariable Long bookingId,
                                               @RequestBody PaymentRequest req) {
        return ResponseEntity.ok(service.pay(bookingId, req));
    }

    @GetMapping
    public PaymentResponse getPayment(@PathVariable Long bookingId) {
        return service.getPayment(bookingId);
    }

    @PostMapping("/refund")
    public RefundResponse refund(@PathVariable Long bookingId,
                                 @RequestBody RefundRequest req) {
        return service.refund(bookingId, req);
    }

    @GetMapping("/refund")
    public RefundResponse getRefund(@PathVariable Long bookingId) {
        return service.getRefund(bookingId);
    }
}
