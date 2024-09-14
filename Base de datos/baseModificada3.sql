-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tiendav2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tiendav2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tiendav2` DEFAULT CHARACTER SET latin1 ;
USE `tiendav2` ;

-- -----------------------------------------------------
-- Table `tiendav2`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`usuario` (
  `idusuario` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `clave` VARCHAR(45) NOT NULL,
  `rol` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tiendav2`.`bitacora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`bitacora` (
  `idbitacora` INT(11) NOT NULL,
  `idUsuario` INT(10) NOT NULL,
  `fecha` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `accion` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idbitacora`),
  INDEX `idUsuario_idx` (`idUsuario` ASC),
  CONSTRAINT `idUsuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `tiendav2`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tiendav2`.`sucursal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`sucursal` (
  `IdSucursal` INT(100) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(30) CHARACTER SET 'latin1' NOT NULL,
  `Direccion` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  `Telefono` VARCHAR(9) CHARACTER SET 'latin1' NOT NULL,
  PRIMARY KEY (`IdSucursal`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`proveedor` (
  `IdProveedor` INT(100) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(30) CHARACTER SET 'latin1' NOT NULL,
  `Telefono` VARCHAR(9) CHARACTER SET 'latin1' NOT NULL,
  `Direccion` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  `NIT` VARCHAR(14) CHARACTER SET 'latin1' NOT NULL,
  `NRC` VARCHAR(7) CHARACTER SET 'latin1' NOT NULL,
  `Email` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  PRIMARY KEY (`IdProveedor`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`compra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`compra` (
  `IdCompra` INT(100) NOT NULL AUTO_INCREMENT,
  `Fecha` DATETIME NOT NULL,
  `IdProveedor` INT(100) NOT NULL,
  `IdSucursal` INT(100) NOT NULL,
  `TipoCompra` CHAR(1) CHARACTER SET 'latin1' NOT NULL,
  `NumDocumento` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  `Subtotal` DOUBLE NOT NULL,
  `IVA` DOUBLE NOT NULL,
  `Percepcion` DOUBLE NOT NULL,
  `Total` DOUBLE NOT NULL,
  PRIMARY KEY USING BTREE (`IdCompra`, `IdSucursal`, `IdProveedor`),
  INDEX `IdSucursal` (`IdSucursal` ASC),
  INDEX `IdProveedor` (`IdProveedor` ASC),
  CONSTRAINT `Compra_ibfk_1`
    FOREIGN KEY (`IdSucursal`)
    REFERENCES `tiendav2`.`sucursal` (`IdSucursal`),
  CONSTRAINT `Compra_ibfk_2`
    FOREIGN KEY (`IdProveedor`)
    REFERENCES `tiendav2`.`proveedor` (`IdProveedor`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`producto` (
  `CodBarra` VARCHAR(13) CHARACTER SET 'latin1' NOT NULL,
  `Nombre` VARCHAR(30) CHARACTER SET 'latin1' NOT NULL,
  `Costo` DOUBLE NOT NULL,
  PRIMARY KEY (`CodBarra`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`inventario` (
  `IdSucursal` INT(100) NOT NULL,
  `CodBarra` VARCHAR(13) CHARACTER SET 'latin1' NOT NULL,
  `Cantidad` DOUBLE NOT NULL,
  INDEX `IdSucursal` (`IdSucursal` ASC),
  INDEX `IdSucursal_2` (`IdSucursal` ASC),
  INDEX `CodBarra` (`CodBarra` ASC),
  CONSTRAINT `Inventario_ibfk_1`
    FOREIGN KEY (`IdSucursal`)
    REFERENCES `tiendav2`.`sucursal` (`IdSucursal`),
  CONSTRAINT `Inventario_ibfk_2`
    FOREIGN KEY (`CodBarra`)
    REFERENCES `tiendav2`.`producto` (`CodBarra`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`detallecompra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`detallecompra` (
  `IdCompra` INT(100) NOT NULL,
  `CodBarra` VARCHAR(13) CHARACTER SET 'latin1' NOT NULL,
  `Cantidad` DOUBLE NOT NULL,
  `CostoUnitario` DOUBLE NOT NULL,
  `IdSucursal` INT(100) NOT NULL,
  PRIMARY KEY USING BTREE (`IdCompra`, `CodBarra`, `IdSucursal`),
  INDEX `IdSucursal` (`IdSucursal` ASC),
  INDEX `CodBarra` (`CodBarra` ASC),
  CONSTRAINT `DetalleCompra_ibfk_1`
    FOREIGN KEY (`IdCompra`)
    REFERENCES `tiendav2`.`compra` (`IdCompra`),
  CONSTRAINT `DetalleCompra_ibfk_2`
    FOREIGN KEY (`IdSucursal`)
    REFERENCES `tiendav2`.`inventario` (`IdSucursal`),
  CONSTRAINT `DetalleCompra_ibfk_3`
    FOREIGN KEY (`CodBarra`)
    REFERENCES `tiendav2`.`producto` (`CodBarra`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`tipoprecio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`tipoprecio` (
  `IdTipoPrecio` INT(100) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(30) CHARACTER SET 'latin1' NOT NULL,
  `Utilidad` DOUBLE NOT NULL,
  PRIMARY KEY (`IdTipoPrecio`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`venta` (
  `IdVenta` INT(100) NOT NULL AUTO_INCREMENT,
  `IdSucursal` INT(100) NOT NULL,
  `TipoVenta` CHAR(1) CHARACTER SET 'latin1' NOT NULL,
  `IdTipoPrecio` INT(100) NOT NULL,
  `Cliente` VARCHAR(30) CHARACTER SET 'latin1' NOT NULL,
  `Fecha` DATETIME NOT NULL,
  `IVA` DOUBLE NOT NULL,
  `TotalGravado` DOUBLE NOT NULL,
  `Total` DOUBLE NOT NULL,
  `Direccion` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  `Giro` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  `NIT` VARCHAR(14) CHARACTER SET 'latin1' NOT NULL,
  `NRC` VARCHAR(7) CHARACTER SET 'latin1' NOT NULL,
  `NDocumento` VARCHAR(100) CHARACTER SET 'latin1' NOT NULL,
  `PAC` DOUBLE NOT NULL,
  `utilidad` DOUBLE NOT NULL,
  PRIMARY KEY (`IdVenta`, `IdSucursal`, `IdTipoPrecio`),
  INDEX `IdTipoPrecio` (`IdTipoPrecio` ASC),
  CONSTRAINT `Venta_ibfk_1`
    FOREIGN KEY (`IdTipoPrecio`)
    REFERENCES `tiendav2`.`tipoprecio` (`IdTipoPrecio`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`detalleventa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`detalleventa` (
  `IdVenta` INT(100) NOT NULL,
  `CodBarra` VARCHAR(13) CHARACTER SET 'latin1' NOT NULL,
  `Cantidad` DOUBLE NOT NULL,
  `PrecioUnitario` DOUBLE NOT NULL,
  `IdSucursal` INT(100) NOT NULL,
  PRIMARY KEY (`IdVenta`, `CodBarra`, `IdSucursal`),
  INDEX `IdSucursal` (`IdSucursal` ASC),
  INDEX `CodBarra` (`CodBarra` ASC),
  CONSTRAINT `DetalleVenta_ibfk_1`
    FOREIGN KEY (`IdVenta`)
    REFERENCES `tiendav2`.`venta` (`IdVenta`),
  CONSTRAINT `DetalleVenta_ibfk_2`
    FOREIGN KEY (`IdSucursal`)
    REFERENCES `tiendav2`.`inventario` (`IdSucursal`),
  CONSTRAINT `DetalleVenta_ibfk_3`
    FOREIGN KEY (`CodBarra`)
    REFERENCES `tiendav2`.`producto` (`CodBarra`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DetalleVenta_1`
    FOREIGN KEY (`IdSucursal`)
    REFERENCES `tiendav2`.`sucursal` (`IdSucursal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


-- -----------------------------------------------------
-- Table `tiendav2`.`parametro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tiendav2`.`parametro` (
  `IdParametro` INT(100) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(30) CHARACTER SET 'latin1' NOT NULL,
  `Valor` VARCHAR(50) CHARACTER SET 'latin1' NOT NULL,
  PRIMARY KEY (`IdParametro`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
