CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name  VARCHAR(100) NOT NULL,
  email      VARCHAR(255) NOT NULL UNIQUE,
  phone      VARCHAR(30),
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  smoking BOOLEAN, bed_type VARCHAR(50), late_checkout BOOLEAN, floor_preference VARCHAR(50)
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role VARCHAR(20) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
