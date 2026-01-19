package com.ey.service;

import com.ey.domain.*;
import com.ey.dto.payment.*;
import com.ey.repo.*;
import com.ey.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final BookingRepo bookingRepo;
    private final PaymentRepo paymentRepo;
    private final RefundRepo refundRepo;
    private final CurrentUser currentUser;

    public PaymentService(BookingRepo bookingRepo,
                          PaymentRepo paymentRepo,
                          RefundRepo refundRepo,
                          CurrentUser currentUser) {
        this.bookingRepo = bookingRepo;
        this.paymentRepo = paymentRepo;
        this.refundRepo = refundRepo;
        this.currentUser = currentUser;
    }

    private void checkOwnerOrAdmin(Booking b) {
        String me = currentUser.email();
        if (me != null && me.equalsIgnoreCase(b.getUserEmail())) return;
        if (currentUser.isAdmin()) return;
        throw new RuntimeException("Forbidden");
    }

    @Transactional
    public PaymentResponse pay(Long bookingId, PaymentRequest req) {

        Booking b = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        checkOwnerOrAdmin(b);

        Payment p = new Payment();
        p.setBooking(b);
        p.setAmount(b.getTotalAmount());
        p.setCurrency(b.getCurrency());
        p.setStatus("SUCCESS");
        p.setCreatedAt(LocalDateTime.now());
        p.setMethod(req.getMethod());
        p.setProviderRef(req.getProviderRef());

        paymentRepo.save(p);

        return PaymentResponse.from(p);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long bookingId) {

        Payment p = paymentRepo.findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("No payment found"));

        checkOwnerOrAdmin(p.getBooking());
        return PaymentResponse.from(p);
    }

    @Transactional
    public RefundResponse refund(Long bookingId, RefundRequest req) {

        Payment p = paymentRepo.findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("No payment found"));

        checkOwnerOrAdmin(p.getBooking());

        Refund r = new Refund();
        r.setPayment(p);
        r.setRefundAmount(p.getAmount());
        r.setCurrency(p.getCurrency());
        r.setReason(req.getReason());
        r.setCreatedAt(LocalDateTime.now());

        refundRepo.save(r);

        return RefundResponse.from(r);
    }

    @Transactional(readOnly = true)
    public RefundResponse getRefund(Long bookingId) {

        Payment p = paymentRepo.findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("No payment found"));

        checkOwnerOrAdmin(p.getBooking());

        return refundRepo.findByPayment_Id(p.getId())
                .stream()
                .findFirst()
                .map(RefundResponse::from)
                .orElseThrow(() -> new RuntimeException("No refund found"));
    }
}
