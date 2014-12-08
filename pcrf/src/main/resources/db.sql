# SQL Manager for MySQL 5.4.3.43929
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : betha


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `betha`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `betha`;

#
# Structure for the `usuario` table : 
#

CREATE TABLE `usuario` (
  `idusuario` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(100) COLLATE utf8_general_ci NOT NULL,
  `senha` VARCHAR(100) COLLATE utf8_general_ci NOT NULL,
  `ativo` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY USING BTREE (`idusuario`) COMMENT '',
  UNIQUE INDEX `login` USING BTREE (`login`) COMMENT ''
)ENGINE=InnoDB
AUTO_INCREMENT=6 AVG_ROW_LENGTH=3276 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT=''
;

#
# Structure for the `compra` table : 
#

CREATE TABLE `compra` (
  `idcompra` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `idusuario` INTEGER(11) NOT NULL,
  `data` DATE NOT NULL,
  `local` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `total` DOUBLE(15,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY USING BTREE (`idcompra`) COMMENT '',
   INDEX `idusuario` USING BTREE (`idusuario`) COMMENT '',
  CONSTRAINT `fk_compra_usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
)ENGINE=InnoDB
AUTO_INCREMENT=5 AVG_ROW_LENGTH=4096 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT=''
;

#
# Structure for the `lista` table : 
#

CREATE TABLE `lista` (
  `idlista` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) COLLATE utf8_general_ci NOT NULL,
  `ativo` TINYINT(1) NOT NULL DEFAULT 1,
  `total` DOUBLE(15,2) NOT NULL DEFAULT 0.00,
  `idusuario` INTEGER(11) NOT NULL,
  PRIMARY KEY USING BTREE (`idlista`) COMMENT '',
   INDEX `idusuario` USING BTREE (`idusuario`) COMMENT '',
  CONSTRAINT `fk_lista_usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
)ENGINE=InnoDB
AUTO_INCREMENT=8 AVG_ROW_LENGTH=2340 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT=''
;

#
# Structure for the `listaitem` table : 
#

CREATE TABLE `listaitem` (
  `iditem` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `idlista` INTEGER(11) NOT NULL,
  `titulo` VARCHAR(100) COLLATE utf8_general_ci NOT NULL,
  `preco` DOUBLE(15,2) NOT NULL DEFAULT 0.00,
  `img` VARCHAR(4000) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY USING BTREE (`iditem`) COMMENT '',
   INDEX `idlista` USING BTREE (`idlista`) COMMENT '',
  CONSTRAINT `fk_listaitem_lista` FOREIGN KEY (`idlista`) REFERENCES `lista` (`idlista`)
)ENGINE=InnoDB
AUTO_INCREMENT=27 AVG_ROW_LENGTH=712 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT=''
;

#
# Structure for the `compraitem` table : 
#

CREATE TABLE `compraitem` (
  `idcompraitem` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `idcompra` INTEGER(11) NOT NULL,
  `idlista` INTEGER(11) DEFAULT NULL,
  `iditem` INTEGER(11) DEFAULT NULL,
  `titulo` VARCHAR(100) COLLATE utf8_general_ci NOT NULL,
  `preco` DOUBLE(15,2) NOT NULL DEFAULT 0.00,
  `img` VARCHAR(4000) COLLATE utf8_general_ci DEFAULT NULL,
  `checked` TINYINT(1) NOT NULL DEFAULT 0,
  `qtd` DOUBLE(15,3) NOT NULL DEFAULT 0.000,
  PRIMARY KEY USING BTREE (`idcompraitem`) COMMENT '',
   INDEX `idcompra` USING BTREE (`idcompra`) COMMENT '',
   INDEX `idlista` USING BTREE (`idlista`) COMMENT '',
   INDEX `iditem` USING BTREE (`iditem`) COMMENT '',
  CONSTRAINT `fk_compraitem_compra` FOREIGN KEY (`idcompra`) REFERENCES `compra` (`idcompra`),
  CONSTRAINT `fk_compraitem_item` FOREIGN KEY (`iditem`) REFERENCES `listaitem` (`iditem`),
  CONSTRAINT `fk_compraitem_lista` FOREIGN KEY (`idlista`) REFERENCES `lista` (`idlista`)
)ENGINE=InnoDB
AUTO_INCREMENT=11 AVG_ROW_LENGTH=1820 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT=''
;



INSERT INTO `usuario` (`idusuario`, `login`, `senha`, `ativo`) VALUES
  (1,'admin','21232f297a57a5a743894a0e4a801fc3',1);
COMMIT;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
