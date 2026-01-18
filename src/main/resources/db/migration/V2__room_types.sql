
CREATE TABLE IF NOT EXISTS room_types (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(200) NOT NULL,
  description TEXT,
  base_price DECIMAL(12,2) NOT NULL,
  max_occupancy INT NOT NULL,
  archived BOOLEAN NOT NULL DEFAULT FALSE
);


CREATE TABLE IF NOT EXISTS room_type_amenities (
  room_type_id BIGINT NOT NULL,
  amenity VARCHAR(100) NOT NULL,
  FOREIGN KEY (room_type_id) REFERENCES room_types(id)
);
