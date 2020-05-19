-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cinework
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cinework` ;

-- -----------------------------------------------------
-- Schema cinework
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinework` DEFAULT CHARACTER SET utf8 ;
USE `cinework` ;

-- -----------------------------------------------------
-- Table `cinework`.`estado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`estado` ;

CREATE TABLE IF NOT EXISTS `cinework`.`estado` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `uf` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `uf_UNIQUE` ON `cinework`.`estado` (`uf` ASC);

CREATE UNIQUE INDEX `nome_UNIQUE` ON `cinework`.`estado` (`nome` ASC);


-- -----------------------------------------------------
-- Table `cinework`.`endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`endereco` ;

CREATE TABLE IF NOT EXISTS `cinework`.`endereco` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `logradouro` VARCHAR(100) NOT NULL,
  `numero` VARCHAR(5) NULL,
  `cidade` VARCHAR(70) NOT NULL,
  `cep` VARCHAR(10) NOT NULL,
  `complemento` VARCHAR(255) NULL,
  `bloco` VARCHAR(45) NULL,
  `apartamento` VARCHAR(7) NULL,
  `estado_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_endereco_estado1`
    FOREIGN KEY (`estado_id`)
    REFERENCES `cinework`.`estado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_endereco_estado1_idx` ON `cinework`.`endereco` (`estado_id` ASC);


-- -----------------------------------------------------
-- Table `cinework`.`empresa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`empresa` ;

CREATE TABLE IF NOT EXISTS `cinework`.`empresa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cnpj` VARCHAR(18) NOT NULL,
  `telefone` VARCHAR(14) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(24) NOT NULL,
  `endereco_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `endereco_id`),
  CONSTRAINT `fk_empresa_endereco`
    FOREIGN KEY (`endereco_id`)
    REFERENCES `cinework`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_empresa_endereco_idx` ON `cinework`.`empresa` (`endereco_id` ASC);

CREATE UNIQUE INDEX `cnpj_UNIQUE` ON `cinework`.`empresa` (`cnpj` ASC);

CREATE UNIQUE INDEX `email_UNIQUE` ON `cinework`.`empresa` (`email` ASC);


-- -----------------------------------------------------
-- Table `cinework`.`combo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`combo` ;

CREATE TABLE IF NOT EXISTS `cinework`.`combo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `valor` DECIMAL(8,2) UNSIGNED NOT NULL,
  `descricao` TEXT(1000) NOT NULL,
  `imagepath` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinework`.`cargo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`cargo` ;

CREATE TABLE IF NOT EXISTS `cinework`.`cargo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `combo_id` BIGINT(20) NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cargo_pacotebeneficio1`
    FOREIGN KEY (`combo_id`)
    REFERENCES `cinework`.`combo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cargo_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `cinework`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_cargo_pacotebeneficio1_idx` ON `cinework`.`cargo` (`combo_id` ASC);

CREATE INDEX `fk_cargo_empresa1_idx` ON `cinework`.`cargo` (`empresa_id` ASC);


-- -----------------------------------------------------
-- Table `cinework`.`funcionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`funcionario` ;

CREATE TABLE IF NOT EXISTS `cinework`.`funcionario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `nascimento` DATE NOT NULL,
  `telefone` VARCHAR(11) NULL,
  `celular` VARCHAR(11) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `pis` VARCHAR(11) NOT NULL,
  `ativo` TINYINT(1) NOT NULL,
  `ultimouso` DATE NULL,
  `endereco_id` BIGINT(20) NOT NULL,
  `cargo_id` BIGINT(20) NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `endereco_id`, `empresa_id`),
  CONSTRAINT `fk_colaborador_endereco1`
    FOREIGN KEY (`endereco_id`)
    REFERENCES `cinework`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_colaborador_cargo1`
    FOREIGN KEY (`cargo_id`)
    REFERENCES `cinework`.`cargo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_funcionario_empresa1`
    FOREIGN KEY (`empresa_id`)
    REFERENCES `cinework`.`empresa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_colaborador_endereco1_idx` ON `cinework`.`funcionario` (`endereco_id` ASC);

CREATE INDEX `fk_colaborador_cargo1_idx` ON `cinework`.`funcionario` (`cargo_id` ASC);

CREATE UNIQUE INDEX `email_UNIQUE` ON `cinework`.`funcionario` (`email` ASC);

CREATE UNIQUE INDEX `celular_UNIQUE` ON `cinework`.`funcionario` (`celular` ASC);

CREATE UNIQUE INDEX `pis_UNIQUE` ON `cinework`.`funcionario` (`pis` ASC);

CREATE UNIQUE INDEX `cpf_UNIQUE` ON `cinework`.`funcionario` (`cpf` ASC);

CREATE INDEX `fk_funcionario_empresa1_idx` ON `cinework`.`funcionario` (`empresa_id` ASC);


-- -----------------------------------------------------
-- Table `cinework`.`beneficio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`beneficio` ;

CREATE TABLE IF NOT EXISTS `cinework`.`beneficio` (
  `id` BIGINT(20) NOT NULL,
  `nome` VARCHAR(70) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `valor` DECIMAL(8,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinework`.`beneficio_combo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinework`.`beneficio_combo` ;

CREATE TABLE IF NOT EXISTS `cinework`.`beneficio_combo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `beneficio_id` BIGINT(20) NOT NULL,
  `combo_id` BIGINT(20) NOT NULL,
  `quantidade` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `beneficio_id`, `combo_id`),
  CONSTRAINT `fk_beneficio_has_pacotebeneficio_beneficio1`
    FOREIGN KEY (`beneficio_id`)
    REFERENCES `cinework`.`beneficio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_beneficio_has_pacotebeneficio_pacotebeneficio1`
    FOREIGN KEY (`combo_id`)
    REFERENCES `cinework`.`combo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_beneficio_has_pacotebeneficio_pacotebeneficio1_idx` ON `cinework`.`beneficio_combo` (`combo_id` ASC);

CREATE INDEX `fk_beneficio_has_pacotebeneficio_beneficio1_idx` ON `cinework`.`beneficio_combo` (`beneficio_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
