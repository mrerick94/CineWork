-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 02-Jun-2020 às 01:40
-- Versão do servidor: 10.4.11-MariaDB
-- versão do PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `cinework`
--
DROP DATABASE IF EXISTS `cinework`;
CREATE DATABASE IF NOT EXISTS `cinework` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cinework`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `beneficio`
--

DROP TABLE IF EXISTS `beneficio`;
CREATE TABLE IF NOT EXISTS `beneficio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(70) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `valor` decimal(8,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `beneficio`
--

INSERT INTO `beneficio` (`id`, `nome`, `descricao`, `valor`) VALUES
(1, 'Vale-ingresso(s)', 'Vale-ingresso que pode ser trocado por ingresso no cinema escolhido.', '10.29'),
(2, 'Vale-Refrigerante(s) médio(s)', 'Cupom que pode ser trocado por refrigerante médio no cinema escolhido', '3.50'),
(3, 'Vale-Pipoca(s) média(s)', 'Cupom que pode ser trocado por pipoca média no cinema escolhido', '5.80');

-- --------------------------------------------------------

--
-- Estrutura da tabela `beneficio_combo`
--

DROP TABLE IF EXISTS `beneficio_combo`;
CREATE TABLE IF NOT EXISTS `beneficio_combo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `beneficio_id` bigint(20) NOT NULL,
  `combo_id` bigint(20) NOT NULL,
  `quantidade` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`,`beneficio_id`,`combo_id`),
  KEY `fk_beneficio_has_pacotebeneficio_combo1_idx` (`combo_id`),
  KEY `fk_beneficio_has_pacotebeneficio_beneficio1_idx` (`beneficio_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `beneficio_combo`
--

INSERT INTO `beneficio_combo` (`id`, `beneficio_id`, `combo_id`, `quantidade`) VALUES
(1, 1, 1, 2),
(2, 3, 1, 1),
(3, 2, 1, 1),
(4, 1, 2, 1),
(5, 2, 2, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cargo`
--

DROP TABLE IF EXISTS `cargo`;
CREATE TABLE IF NOT EXISTS `cargo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `combo_id` bigint(20) DEFAULT NULL,
  `empresa_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cargo_pacotebeneficio1_idx` (`combo_id`),
  KEY `fk_cargo_empresa1_idx` (`empresa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `cargo`
--

INSERT INTO `cargo` (`id`, `nome`, `combo_id`, `empresa_id`) VALUES
(1, 'Analista de Sistemas', NULL, 1),
(2, 'Segurança', NULL, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `combo`
--

DROP TABLE IF EXISTS `combo`;
CREATE TABLE IF NOT EXISTS `combo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `valor` decimal(8,2) UNSIGNED NOT NULL,
  `descricao` text NOT NULL,
  `imagepath` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `combo`
--

INSERT INTO `combo` (`id`, `valor`, `descricao`, `imagepath`) VALUES
(1, '156.79', 'Pacote Premium', 'pacotepremium.png'),
(2, '47.85', 'Pacote Médio', 'pacotemedio.png');

-- --------------------------------------------------------

--
-- Estrutura da tabela `empresa`
--

DROP TABLE IF EXISTS `empresa`;
CREATE TABLE IF NOT EXISTS `empresa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `telefone` varchar(14) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(24) NOT NULL,
  `endereco_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`,`endereco_id`),
  UNIQUE KEY `cnpj_UNIQUE` (`cnpj`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_empresa_endereco_idx` (`endereco_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `empresa`
--

INSERT INTO `empresa` (`id`, `nome`, `cnpj`, `telefone`, `email`, `senha`, `endereco_id`) VALUES
(1, 'Erick', '88.888.888/8888-88', '(55)55555-5555', 'erick@erick.com', 'erick', 1),
(2, 'AAAAAAAAAAAAAA', '22.222.222/2222-22', '(33)33333-3333', 'AAAAAAAAAAAAAAAAAAAAAAAA', 'aaa', 2),
(5, 'AAAAAAAAAAAAAA', '66.666.666/6666-66', '(66)66666-6666', 'NNNNNNNNNNNNNNNNNNNNNN', 'aaa', 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

DROP TABLE IF EXISTS `endereco`;
CREATE TABLE IF NOT EXISTS `endereco` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(5) DEFAULT NULL,
  `cidade` varchar(70) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `bloco` varchar(45) DEFAULT NULL,
  `apartamento` varchar(7) DEFAULT NULL,
  `estado_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_endereco_estado1_idx` (`estado_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `endereco`
--

INSERT INTO `endereco` (`id`, `logradouro`, `numero`, `cidade`, `cep`, `complemento`, `bloco`, `apartamento`, `estado_id`) VALUES
(1, 'Alameda dos Ipes', '73', 'Palhoça', '22.222-222', '', '4', '137', 1),
(2, 'AAAAAAAAAAAAAAAAAAAAAAAA', '33', 'AAAAAAAAAAAAAAAAAAAA', '33.333-333', '', 'AAAAAA', 'AAA', 2),
(5, 'AAAAAAAAAAAAAAAAAAAAAAAA', '66', 'AAAAAAAAAAAAAAAAAAAA', '66.666-666', '', 'AAAAAA', '66', 2),
(9, 'AAAAAAAAAAAAAAAAAAAAAAAA', '554', 'AAAAAAAAAAAAAAAAAAAA', '44.444-444', 'fffffffffffffffffffffffff', 'AAAAAA', 'fdfsdfd', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `estado`
--

DROP TABLE IF EXISTS `estado`;
CREATE TABLE IF NOT EXISTS `estado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `uf` varchar(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uf_UNIQUE` (`uf`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `estado`
--

INSERT INTO `estado` (`id`, `nome`, `uf`) VALUES
(1, 'Santa Catarina', 'SC'),
(2, 'Rio Grande do Sul', 'RS');

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
CREATE TABLE IF NOT EXISTS `funcionario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(60) NOT NULL,
  `nascimento` date NOT NULL,
  `telefone` varchar(14) DEFAULT NULL,
  `celular` varchar(14) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `pis` varchar(14) NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  `ultimouso` date DEFAULT NULL,
  `endereco_id` bigint(20) NOT NULL,
  `cargo_id` bigint(20) NOT NULL,
  `empresa_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`,`endereco_id`,`empresa_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `celular_UNIQUE` (`celular`),
  UNIQUE KEY `pis_UNIQUE` (`pis`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  KEY `fk_colaborador_endereco1_idx` (`endereco_id`),
  KEY `fk_colaborador_cargo1_idx` (`cargo_id`),
  KEY `fk_funcionario_empresa1_idx` (`empresa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `funcionario`
--

INSERT INTO `funcionario` (`id`, `nome`, `email`, `nascimento`, `telefone`, `celular`, `cpf`, `pis`, `ativo`, `ultimouso`, `endereco_id`, `cargo_id`, `empresa_id`) VALUES
(1, 'Robson Tavares', 'robson@robson.com', '1993-03-23', '(33)33333-3333', '(33)33333-3333', '222.222.222-22', '333.3333.333-3', 1, NULL, 9, 2, 1);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `beneficio_combo`
--
ALTER TABLE `beneficio_combo`
  ADD CONSTRAINT `fk_beneficio_has_pacotebeneficio_beneficio1` FOREIGN KEY (`beneficio_id`) REFERENCES `beneficio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_beneficio_has_pacotebeneficio_combo1_idx` FOREIGN KEY (`combo_id`) REFERENCES `combo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `cargo`
--
ALTER TABLE `cargo`
  ADD CONSTRAINT `fk_cargo_empresa1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cargo_pacotebeneficio1` FOREIGN KEY (`combo_id`) REFERENCES `combo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `empresa`
--
ALTER TABLE `empresa`
  ADD CONSTRAINT `fk_empresa_endereco` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `endereco`
--
ALTER TABLE `endereco`
  ADD CONSTRAINT `fk_endereco_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `funcionario`
--
ALTER TABLE `funcionario`
  ADD CONSTRAINT `fk_colaborador_cargo1` FOREIGN KEY (`cargo_id`) REFERENCES `cargo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_colaborador_endereco1` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_funcionario_empresa1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
