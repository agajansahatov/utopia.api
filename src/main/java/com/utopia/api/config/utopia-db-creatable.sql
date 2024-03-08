-- Create the database with utf8mb4_unicode_ci collation
CREATE DATABASE utopia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Switch to the utopia database
USE utopia;

-- Users table
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  contact VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL,
  name VARCHAR(255),
  balance DECIMAL(15, 2),
  address VARCHAR(500),
  image VARCHAR(255),
  auth_time DATETIME,
  PRIMARY KEY (id)
);

-- Products table
CREATE TABLE products (
  id BIGINT NOT NULL AUTO_INCREMENT,
  image_name VARCHAR(255),
  name VARCHAR(255),
  price DECIMAL(15, 2),
  category VARCHAR(250),
  description TEXT,
  date DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

-- Orders table
CREATE TABLE orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT,
  product_id BIGINT,
  destination VARCHAR(255),
  status VARCHAR(255),
  quantity INT,
  date DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id),
  INDEX idx_user_id (user_id),
  INDEX idx_product_id (product_id)
);

-- Favourites table
CREATE TABLE favourites (
  user_id BIGINT,
  product_id BIGINT,
  date DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, product_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id),
  INDEX idx_user_id (user_id),
  INDEX idx_product_id (product_id)
);

-- Traces table
CREATE TABLE traces (
  user_id BIGINT,
  product_id BIGINT,
  date DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, product_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id),
  INDEX idx_user_id (user_id),
  INDEX idx_product_id (product_id)
);

-- Interactions table
CREATE TABLE interactions (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NULL,
  ip VARCHAR(255),
  device VARCHAR(255),
  source VARCHAR(255),
  page VARCHAR(255),
  date DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX idx_user_id (user_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE
);

