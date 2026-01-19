CREATE TABLE IF NOT EXISTS availability (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  room_type_id BIGINT NOT NULL,
  date DATE NOT NULL,
  available_count INT NOT NULL,
  price DECIMAL(12,2),
  CONSTRAINT uq_avail UNIQUE (room_type_id, date),
  CONSTRAINT fk_avail_room_type FOREIGN KEY (room_type_id) REFERENCES room_types(id)
);


CREATE TABLE IF NOT EXISTS availability_holds (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  hold_code VARCHAR(50) UNIQUE NOT NULL,
  room_type_id BIGINT NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  guests INT NOT NULL,
  expires_at TIMESTAMP NOT NULL,  
  user_email VARCHAR(255),
  CONSTRAINT fk_hold_room_type FOREIGN KEY (room_type_id) REFERENCES room_types(id)
);

CREATE INDEX idx_availability_holds_rt_exp
  ON availability_holds (room_type_id, expires_at);
