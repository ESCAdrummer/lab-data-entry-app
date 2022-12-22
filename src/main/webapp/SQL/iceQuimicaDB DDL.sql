-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema iceQuimica
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema iceQuimica
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `iceQuimica` DEFAULT CHARACTER SET utf8 ;
USE `iceQuimica` ;

-- -----------------------------------------------------
-- Table `iceQuimica`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iceQuimica`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isAdmin` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iceQuimica`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iceQuimica`.`test` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `units` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iceQuimica`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iceQuimica`.`products` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iceQuimica`.`qualitySet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iceQuimica`.`qualitySet` (
  `test_id` INT UNSIGNED NOT NULL,
  `products_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`test_id`, `products_id`),
  INDEX `fk_qualitySet_products1_idx` (`products_id` ASC) VISIBLE,
  CONSTRAINT `fk_qualitySet_test`
    FOREIGN KEY (`test_id`)
    REFERENCES `iceQuimica`.`test` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_qualitySet_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `iceQuimica`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iceQuimica`.`testResults`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iceQuimica`.`testResults` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  `products_id` INT UNSIGNED NOT NULL,
  `test_id` INT UNSIGNED NOT NULL,
  `value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `products_id`, `test_id`),
  INDEX `fk_testResults_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_testResults_products1_idx` (`products_id` ASC) VISIBLE,
  INDEX `fk_testResults_test1_idx` (`test_id` ASC) VISIBLE,
  CONSTRAINT `fk_testResults_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `iceQuimica`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_testResults_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `iceQuimica`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_testResults_test1`
    FOREIGN KEY (`test_id`)
    REFERENCES `iceQuimica`.`test` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
