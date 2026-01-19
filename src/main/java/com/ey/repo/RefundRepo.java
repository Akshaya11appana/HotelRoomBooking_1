package com.ey.repo;

import com.ey.domain.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepo extends JpaRepository<Refund, Long> {
    List<Refund> findByPayment_Id(Long paymentId);
}
