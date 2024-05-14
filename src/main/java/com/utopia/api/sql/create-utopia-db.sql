-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema utopia
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `utopia` ;

-- -----------------------------------------------------
-- Schema utopia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `utopia` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `utopia` ;

-- -----------------------------------------------------
-- Table `utopia`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`categories` ;

CREATE TABLE IF NOT EXISTS `utopia`.`categories` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`roles` ;

CREATE TABLE IF NOT EXISTS `utopia`.`roles` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`users` ;

CREATE TABLE IF NOT EXISTS `utopia`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `contact` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role_id` TINYINT NOT NULL,
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NULL DEFAULT NULL,
  `balance` DECIMAL(15,2) UNSIGNED NULL DEFAULT NULL,
  `country` VARCHAR(50) NOT NULL,
  `province` VARCHAR(50) NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `address` VARCHAR(500) NOT NULL,
  `auth_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_users_user_roles1_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `contact_UNIQUE` (`contact` ASC) VISIBLE,
  CONSTRAINT `fk_users_user_roles`
    FOREIGN KEY (`role_id`)
    REFERENCES `utopia`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `utopia`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`products` ;

CREATE TABLE IF NOT EXISTS `utopia`.`products` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `original_price` DECIMAL(15,2) NOT NULL,
  `sales_price` DECIMAL(15,2) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `properties` JSON NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `utopia`.`favourites`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`favourites` ;

CREATE TABLE IF NOT EXISTS `utopia`.`favourites` (
  `user_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `product_id`),
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_product_id` (`product_id` ASC) VISIBLE,
  CONSTRAINT `favourites_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `utopia`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `favourites_ibfk_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `utopia`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `utopia`.`statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`statuses` ;

CREATE TABLE IF NOT EXISTS `utopia`.`statuses` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`payment_methods`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`payment_methods` ;

CREATE TABLE IF NOT EXISTS `utopia`.`payment_methods` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`shippers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`shippers` ;

CREATE TABLE IF NOT EXISTS `utopia`.`shippers` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`orders` ;

CREATE TABLE IF NOT EXISTS `utopia`.`orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `order_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shipped_date` DATETIME NULL DEFAULT NULL,
  `shipper_id` TINYINT NULL DEFAULT NULL,
  `payment_method_id` TINYINT NOT NULL,
  `status_id` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_product_id` (`product_id` ASC) VISIBLE,
  INDEX `fk_orders_order_statuses1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_orders_payment_methods1_idx` (`payment_method_id` ASC) VISIBLE,
  INDEX `fk_orders_shippers1_idx` (`shipper_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_order_statuses`
    FOREIGN KEY (`status_id`)
    REFERENCES `utopia`.`statuses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_payment_methods`
    FOREIGN KEY (`payment_method_id`)
    REFERENCES `utopia`.`payment_methods` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_shippers`
    FOREIGN KEY (`shipper_id`)
    REFERENCES `utopia`.`shippers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `utopia`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `utopia`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `utopia`.`product_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`product_categories` ;

CREATE TABLE IF NOT EXISTS `utopia`.`product_categories` (
  `product_id` BIGINT NOT NULL,
  `category_id` TINYINT NOT NULL,
  PRIMARY KEY (`product_id`, `category_id`),
  INDEX `fk_product_categories_categories1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_categories_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `utopia`.`categories` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_categories_products`
    FOREIGN KEY (`product_id`)
    REFERENCES `utopia`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`product_images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`product_images` ;

CREATE TABLE IF NOT EXISTS `utopia`.`product_images` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `name` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_product_images_products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_images_products`
    FOREIGN KEY (`product_id`)
    REFERENCES `utopia`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`product_videos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`product_videos` ;

CREATE TABLE IF NOT EXISTS `utopia`.`product_videos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `name` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_videos_products1_idx` (`product_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_product_videos_products`
    FOREIGN KEY (`product_id`)
    REFERENCES `utopia`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `utopia`.`traces`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `utopia`.`traces` ;

CREATE TABLE IF NOT EXISTS `utopia`.`traces` (
  `user_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `product_id`),
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_product_id` (`product_id` ASC) VISIBLE,
  CONSTRAINT `traces_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `utopia`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `traces_ibfk_2`
    FOREIGN KEY (`product_id`)
    REFERENCES `utopia`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
