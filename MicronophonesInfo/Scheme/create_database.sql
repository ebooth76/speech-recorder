-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema Micronophones
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Micronophones` ;

-- -----------------------------------------------------
-- Schema Micronophones
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Micronophones` ;
USE `Micronophones` ;

-- -----------------------------------------------------
-- Table `Micronophones`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`users` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`users` (
  `idusers` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(256) NOT NULL,
  `password` VARCHAR(1024) NOT NULL,
  `usertype` VARCHAR(256) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `firstname` VARCHAR(256) NOT NULL,
  `lastname` VARCHAR(256) NOT NULL,
  `gender` VARCHAR(24) NOT NULL,
  `age` INT NOT NULL,
  `verified_user` TINYINT NOT NULL,
  PRIMARY KEY (`idusers`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Micronophones`.`researcher_secret_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`researcher_secret_user` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`researcher_secret_user` (
  `idresearcher_secret_user` INT NOT NULL AUTO_INCREMENT,
  `age` INT NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  `location` VARCHAR(256) NOT NULL,
  `time` DATETIME NOT NULL,
  `speech_type` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`idresearcher_secret_user`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Micronophones`.`voice_analysis_metadata`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`voice_analysis_metadata` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`voice_analysis_metadata` (
  `idvoice_analysis` INT NOT NULL AUTO_INCREMENT,
  `phoneme_insertions` FLOAT NULL,
  `phoneme_deletions` FLOAT NULL,
  `phoneme_replacements` FLOAT NULL,
  `total_phonemes` FLOAT NULL,
  PRIMARY KEY (`idvoice_analysis`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Micronophones`.`researcher_voice_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`researcher_voice_data` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`researcher_voice_data` (
  `idresearcher_voice_data` INT NOT NULL AUTO_INCREMENT,
  `path_to_voice_data` VARCHAR(1024) NOT NULL,
  `private` TINYINT NOT NULL,
  `users_idusers` INT NOT NULL,
  `researcher_secret_user_idresearcher_secret_user` INT NOT NULL,
  `voice_analysis_idvoice_analysis` INT NOT NULL,
  PRIMARY KEY (`idresearcher_voice_data`, `users_idusers`, `researcher_secret_user_idresearcher_secret_user`, `voice_analysis_idvoice_analysis`),
  INDEX `fk_researcher_voice_data_users1_idx` (`users_idusers` ASC),
  INDEX `fk_researcher_voice_data_researcher_secret_user1_idx` (`researcher_secret_user_idresearcher_secret_user` ASC),
  INDEX `fk_researcher_voice_data_voice_analysis1_idx` (`voice_analysis_idvoice_analysis` ASC),
  CONSTRAINT `fk_researcher_voice_data_users1`
    FOREIGN KEY (`users_idusers`)
    REFERENCES `Micronophones`.`users` (`idusers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_researcher_voice_data_researcher_secret_user1`
    FOREIGN KEY (`researcher_secret_user_idresearcher_secret_user`)
    REFERENCES `Micronophones`.`researcher_secret_user` (`idresearcher_secret_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_researcher_voice_data_voice_analysis1`
    FOREIGN KEY (`voice_analysis_idvoice_analysis`)
    REFERENCES `Micronophones`.`voice_analysis_metadata` (`idvoice_analysis`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Micronophones`.`patient_voice_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`patient_voice_data` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`patient_voice_data` (
  `idpatient_voice_data` INT NOT NULL AUTO_INCREMENT,
  `path_to_voice_data` VARCHAR(1024) NOT NULL,
  `users_idusers` INT NOT NULL,
  `voice_analysis_idvoice_analysis` INT NOT NULL,
  PRIMARY KEY (`idpatient_voice_data`, `users_idusers`, `voice_analysis_idvoice_analysis`),
  INDEX `fk_patient_voice_data_users_idx` (`users_idusers` ASC),
  INDEX `fk_patient_voice_data_voice_analysis1_idx` (`voice_analysis_idvoice_analysis` ASC),
  CONSTRAINT `fk_patient_voice_data_users`
    FOREIGN KEY (`users_idusers`)
    REFERENCES `Micronophones`.`users` (`idusers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_voice_data_voice_analysis1`
    FOREIGN KEY (`voice_analysis_idvoice_analysis`)
    REFERENCES `Micronophones`.`voice_analysis_metadata` (`idvoice_analysis`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Micronophones`.`Scripts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`Scripts` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`Scripts` (
  `idScripts` INT NOT NULL AUTO_INCREMENT,
  `script_name` VARCHAR(128) NOT NULL,
  `script` LONGTEXT NOT NULL,
  `private` TINYINT NOT NULL,
  `users_idusers` INT NOT NULL,
  PRIMARY KEY (`idScripts`, `users_idusers`),
  INDEX `fk_Scripts_users1_idx` (`users_idusers` ASC),
  CONSTRAINT `fk_Scripts_users1`
    FOREIGN KEY (`users_idusers`)
    REFERENCES `Micronophones`.`users` (`idusers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Micronophones`.`random_text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Micronophones`.`random_text` ;

CREATE TABLE IF NOT EXISTS `Micronophones`.`random_text` (
  `idrandom_text` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`idrandom_text`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
