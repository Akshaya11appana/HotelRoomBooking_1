CREATE TABLE IF NOT EXISTS payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  booking_id BIGINT NOT NULL,
  amount DECIMAL(12,2) NOT NULL,
  currency VARCHAR(10) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  method VARCHAR(50),
  provider_ref VARCHAR(100),
  CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS refunds (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_id BIGINT NOT NULL,
  refund_amount DECIMAL(12,2) NOT NULL,
  currency VARCHAR(10) NOT NULL,
  reason VARCHAR(255),
  created_at TIMESTAMP NOT NULL,
  CONSTRAINT fk_refund_payment FOREIGN KEY (payment_id) REFERENCES payments(id)
);
