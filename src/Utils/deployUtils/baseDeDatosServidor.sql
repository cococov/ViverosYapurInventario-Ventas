-- --------------------------------------------------------
-- Host:                         192.168.1.1
-- Versión del servidor:         5.7.24 - MySQL Community Server (GPL)
-- SO del servidor:              Linux
-- HeidiSQL Versión:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para proyecto_yapur
DROP DATABASE IF EXISTS `proyecto_yapur`;
CREATE DATABASE IF NOT EXISTS `proyecto_yapur` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `proyecto_yapur`;

-- Volcando estructura para tabla proyecto_yapur.accesorio
DROP TABLE IF EXISTS `accesorio`;
CREATE TABLE IF NOT EXISTS `accesorio` (
  `codproducto` varchar(500) NOT NULL DEFAULT '0',
  PRIMARY KEY (`codproducto`),
  CONSTRAINT `accesorio_fk` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.accesorio: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `accesorio` DISABLE KEYS */;
/*!40000 ALTER TABLE `accesorio` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.cambios
DROP TABLE IF EXISTS `cambios`;
CREATE TABLE IF NOT EXISTS `cambios` (
  `idcambio` int(11) NOT NULL AUTO_INCREMENT,
  `rutusuario` varchar(20) DEFAULT NULL,
  `descripcioncambio` varchar(500) DEFAULT NULL,
  `fechacambio` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcambio`),
  KEY `cambio_usuario` (`rutusuario`),
  CONSTRAINT `cambio_usuario` FOREIGN KEY (`rutusuario`) REFERENCES `usuario` (`rutusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.cambios: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `cambios` DISABLE KEYS */;
/*!40000 ALTER TABLE `cambios` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.cheques
DROP TABLE IF EXISTS `cheques`;
CREATE TABLE IF NOT EXISTS `cheques` (
  `codcheque` int(11) NOT NULL AUTO_INCREMENT,
  `numerocheque` varchar(100) DEFAULT NULL,
  `fecharecepcion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fechavencimiento` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `montocheque` varchar(500) DEFAULT NULL,
  `descripcioncheque` varchar(500) DEFAULT NULL,
  `nombresemisor` varchar(500) DEFAULT NULL,
  `apellidosemisor` varchar(500) DEFAULT NULL,
  `banco` varchar(500) DEFAULT NULL,
  `numerocuenta` int(11) DEFAULT NULL,
  `chequescobrados_n` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codcheque`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.cheques: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `cheques` DISABLE KEYS */;
/*!40000 ALTER TABLE `cheques` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.compra
DROP TABLE IF EXISTS `compra`;
CREATE TABLE IF NOT EXISTS `compra` (
  `codcompra` int(11) NOT NULL DEFAULT '0',
  `tipopago` text,
  `metodopago` text,
  PRIMARY KEY (`codcompra`),
  CONSTRAINT `compra_fk` FOREIGN KEY (`codcompra`) REFERENCES `ordencompra` (`codordencompra`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.compra: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.especie
DROP TABLE IF EXISTS `especie`;
CREATE TABLE IF NOT EXISTS `especie` (
  `codespecie` int(11) NOT NULL AUTO_INCREMENT,
  `codtipo` int(11) DEFAULT NULL,
  `nombreespecie` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`codespecie`),
  KEY `especie_fk` (`codtipo`),
  CONSTRAINT `especie_fk` FOREIGN KEY (`codtipo`) REFERENCES `tipo` (`codtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.especie: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `especie` DISABLE KEYS */;
/*!40000 ALTER TABLE `especie` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.merma
DROP TABLE IF EXISTS `merma`;
CREATE TABLE IF NOT EXISTS `merma` (
  `codmerma` int(11) NOT NULL AUTO_INCREMENT,
  `cantidadmerma` int(11) DEFAULT NULL,
  `fechamerma` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `descripcionmerma` varchar(500) DEFAULT NULL,
  `codproducto` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`codmerma`),
  KEY `merma_fk` (`codproducto`),
  CONSTRAINT `merma_fk` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.merma: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `merma` DISABLE KEYS */;
/*!40000 ALTER TABLE `merma` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.ordencompra
DROP TABLE IF EXISTS `ordencompra`;
CREATE TABLE IF NOT EXISTS `ordencompra` (
  `codordencompra` int(11) NOT NULL AUTO_INCREMENT,
  `totalcondescuento` int(11) DEFAULT NULL,
  `totalsindescuento` int(11) DEFAULT NULL,
  `totalneto` int(11) DEFAULT NULL,
  `efectivo` int(11) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `folio` varchar(500) NOT NULL,
  PRIMARY KEY (`codordencompra`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.ordencompra: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `ordencompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordencompra` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.planta
DROP TABLE IF EXISTS `planta`;
CREATE TABLE IF NOT EXISTS `planta` (
  `codproducto` varchar(11) NOT NULL DEFAULT '0',
  `codespecie` int(11) DEFAULT NULL,
  PRIMARY KEY (`codproducto`),
  KEY `planta_especie_fk` (`codespecie`),
  CONSTRAINT `planta_especie_fk` FOREIGN KEY (`codespecie`) REFERENCES `especie` (`codespecie`),
  CONSTRAINT `planta_fk` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.planta: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `planta` DISABLE KEYS */;
/*!40000 ALTER TABLE `planta` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.preciohistoricoproducto
DROP TABLE IF EXISTS `preciohistoricoproducto`;
CREATE TABLE IF NOT EXISTS `preciohistoricoproducto` (
  `codproducto` varchar(500) NOT NULL DEFAULT '0',
  `fechaproducto` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `precioproductoneto` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`codproducto`,`fechaproducto`),
  CONSTRAINT `preciohistoricoproducto_fk` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.preciohistoricoproducto: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `preciohistoricoproducto` DISABLE KEYS */;
/*!40000 ALTER TABLE `preciohistoricoproducto` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.presupuesto
DROP TABLE IF EXISTS `presupuesto`;
CREATE TABLE IF NOT EXISTS `presupuesto` (
  `codpresupuesto` int(11) NOT NULL DEFAULT '0',
  `descripcion` varchar(500) NOT NULL,
  PRIMARY KEY (`codpresupuesto`),
  CONSTRAINT `presupuesto_fk` FOREIGN KEY (`codpresupuesto`) REFERENCES `ordencompra` (`codordencompra`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.presupuesto: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `presupuesto` DISABLE KEYS */;
/*!40000 ALTER TABLE `presupuesto` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `codproducto` varchar(500) NOT NULL,
  `nombreproducto` varchar(50) DEFAULT NULL,
  `cantidadproductoventa` int(11) DEFAULT NULL,
  `cantidadproductoproduccion` int(11) DEFAULT NULL,
  `descripcionproducto` varchar(500) DEFAULT NULL,
  `stockminimo` text,
  PRIMARY KEY (`codproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.producto: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.productoordencompra
DROP TABLE IF EXISTS `productoordencompra`;
CREATE TABLE IF NOT EXISTS `productoordencompra` (
  `codproducto` varchar(500) NOT NULL DEFAULT '0',
  `codordencompra` int(11) NOT NULL DEFAULT '0',
  `cantidadproductoordencompra` int(11) DEFAULT NULL,
  PRIMARY KEY (`codproducto`,`codordencompra`),
  KEY `productoordencompra_fk` (`codordencompra`),
  CONSTRAINT `productoordencompra_fk` FOREIGN KEY (`codordencompra`) REFERENCES `ordencompra` (`codordencompra`),
  CONSTRAINT `productoordencompra_pro_fk` FOREIGN KEY (`codproducto`) REFERENCES `producto` (`codproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.productoordencompra: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `productoordencompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `productoordencompra` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.proveedor
DROP TABLE IF EXISTS `proveedor`;
CREATE TABLE IF NOT EXISTS `proveedor` (
  `codproveedor` int(11) NOT NULL AUTO_INCREMENT,
  `nombreproveedor` varchar(50) DEFAULT NULL,
  `descripcionproveedor` varchar(500) DEFAULT NULL,
  `apellidosproveedor` varchar(50) DEFAULT NULL,
  `contactoproveedor` varchar(20) DEFAULT NULL,
  `correoproveedor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codproveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.proveedor: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.rol
DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `idrol` int(11) NOT NULL AUTO_INCREMENT,
  `nombrerol` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idrol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.rol: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`idrol`, `nombrerol`) VALUES
	(1, 'administrador'),
	(2, 'vendedor'),
	(3, 'inventario');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.tipo
DROP TABLE IF EXISTS `tipo`;
CREATE TABLE IF NOT EXISTS `tipo` (
  `codtipo` int(11) NOT NULL AUTO_INCREMENT,
  `nombretipo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`codtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.tipo: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;

-- Volcando estructura para tabla proyecto_yapur.usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `rutusuario` varchar(20) NOT NULL,
  `nombreusuario` varchar(50) DEFAULT NULL,
  `passwd` varchar(500) DEFAULT NULL,
  `apellidopaterno` varchar(20) DEFAULT NULL,
  `apellidomaterno` varchar(20) DEFAULT NULL,
  `bloqueadoS_N` tinyint(1) DEFAULT NULL,
  `idrol` int(11) DEFAULT NULL,
  PRIMARY KEY (`rutusuario`),
  KEY `rol_usuario_fk` (`idrol`),
  CONSTRAINT `rol_usuario_fk` FOREIGN KEY (`idrol`) REFERENCES `rol` (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla proyecto_yapur.usuario: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`rutusuario`, `nombreusuario`, `passwd`, `apellidopaterno`, `apellidomaterno`, `bloqueadoS_N`, `idrol`) VALUES
	('10.684.645-6', 'hernan', 'yapur', 'yapur', 'goya', 0, 1),
	('12.706.974-3', 'julieta catalina', 'alamo01', 'alamo', 'parra', 0, 1),
	('19.039.872-2', 'Juan Carlos', 'lamasalfaro', 'Lamas', 'Alfaro', 0, 1),
	('19.321.992-6', 'Domingo Antonio', 'domingo', 'Pinto', 'Torres', 0, 1),
	('9.864.382-6', 'qqqqqq', '123', 'eeeee', 'rrrrrr', 0, 1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
