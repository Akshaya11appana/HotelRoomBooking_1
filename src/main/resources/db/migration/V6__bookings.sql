CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  booking_code VARCHAR(50) UNIQUE NOT NULL,
  user_id BIGINT NULL,
  user_email VARCHAR(255) NULL,
  room_type_id BIGINT NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  guests INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  currency VARCHAR(10) NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL,
  discount DECIMAL(12,2) NOT NULL,
  tax_total DECIMAL(12,2) NOT NULL,
  total_amount DECIMAL(12,2) NOT NULL,
  hold_code VARCHAR(50) NULL,
  created_at TIMESTAMP NOT NULL,
  CONSTRAINT fk_bkg_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_bkg_room_type FOREIGN KEY (room_type_id) REFERENCES room_types(id)
);

CREATE TABLE IF NOT EXISTS booking_notes (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  booking_id BIGINT NOT NULL,
  note TEXT NOT NULL,
  author VARCHAR(100),
  created_at TIMESTAMP NOT NULL,
  CONSTRAINT fk_bkg_note FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE INDEX idx_bkg_user ON bookings(user_id);
CREATE INDEX idx_bkg_email ON bookings(user_email);
CREATE INDEX idx_bkg_rt_dates ON bookings(room_type_id, check_in, check_out);
