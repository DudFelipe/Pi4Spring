-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: pi4
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `atualizacao_pedido`
--

DROP TABLE IF EXISTS `atualizacao_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `atualizacao_pedido` (
  `idAtualizacao` int(11) NOT NULL AUTO_INCREMENT,
  `idPedido` int(11) DEFAULT NULL,
  `mensagem` text,
  `data` date DEFAULT NULL,
  PRIMARY KEY (`idAtualizacao`),
  KEY `idPedido_idx` (`idPedido`),
  CONSTRAINT `atualizacaopedidoIdPedidoFK` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idpedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atualizacao_pedido`
--

LOCK TABLES `atualizacao_pedido` WRITE;
/*!40000 ALTER TABLE `atualizacao_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `atualizacao_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalhes_pedido`
--

DROP TABLE IF EXISTS `detalhes_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `detalhes_pedido` (
  `idPedido` int(11) NOT NULL AUTO_INCREMENT,
  `idProduto` int(11) DEFAULT NULL,
  `quantidade` int(11) DEFAULT '1',
  KEY `idProduto_idx` (`idProduto`),
  KEY `idPedido_idx` (`idPedido`),
  CONSTRAINT `idPedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idpedido`),
  CONSTRAINT `idProduto` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`idproduto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalhes_pedido`
--

LOCK TABLES `detalhes_pedido` WRITE;
/*!40000 ALTER TABLE `detalhes_pedido` DISABLE KEYS */;
INSERT INTO `detalhes_pedido` VALUES (2,NULL,2);
/*!40000 ALTER TABLE `detalhes_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enderecoentrega`
--

DROP TABLE IF EXISTS `enderecoentrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `enderecoentrega` (
  `idEnderecoEntrega` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) DEFAULT NULL,
  `rua` varchar(200) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `cep` int(8) DEFAULT NULL,
  `bairro` varchar(45) DEFAULT NULL,
  `referencia` varchar(60) DEFAULT NULL,
  `apelido` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idEnderecoEntrega`),
  KEY `idUsuarioEndereco_idx` (`idUsuario`),
  CONSTRAINT `idUsuarioEndereco` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enderecoentrega`
--

LOCK TABLES `enderecoentrega` WRITE;
/*!40000 ALTER TABLE `enderecoentrega` DISABLE KEYS */;
/*!40000 ALTER TABLE `enderecoentrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pedido` (
  `idPedido` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `idFuncionario` int(11) NOT NULL,
  `idTipoPagamento` int(11) NOT NULL,
  `data` date DEFAULT NULL,
  `precoVenda` double NOT NULL,
  PRIMARY KEY (`idPedido`),
  KEY `idCliente_idx` (`idCliente`),
  KEY `idTipoPagamento_idx` (`idTipoPagamento`),
  KEY `idFuncionario_idx` (`idFuncionario`),
  CONSTRAINT `idCliente` FOREIGN KEY (`idCliente`) REFERENCES `usuario` (`idusuario`),
  CONSTRAINT `idFuncionario` FOREIGN KEY (`idFuncionario`) REFERENCES `usuario` (`idusuario`),
  CONSTRAINT `idTipoPagamento` FOREIGN KEY (`idTipoPagamento`) REFERENCES `tipopagamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (2,1,1,1,'2010-10-10',50),(3,1,4,1,'2010-10-10',40);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `produto` (
  `idProduto` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `preco` double NOT NULL,
  `fabricante` varchar(60) DEFAULT NULL,
  `estoque` int(11) NOT NULL DEFAULT '0',
  `modelo` varchar(60) DEFAULT NULL,
  `codigodebarras` varchar(45) DEFAULT NULL,
  `descricao` text,
  `idCategoria` int(2) NOT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `idCategoriaFK_idx` (`idCategoria`),
  CONSTRAINT `idCategoriaFK` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idcategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipopagamento`
--

DROP TABLE IF EXISTS `tipopagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tipopagamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipopagamento`
--

LOCK TABLES `tipopagamento` WRITE;
/*!40000 ALTER TABLE `tipopagamento` DISABLE KEYS */;
INSERT INTO `tipopagamento` VALUES (1,'A');
/*!40000 ALTER TABLE `tipopagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(14) NOT NULL,
  `nome` varchar(70) NOT NULL,
  `nascimento` date NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `email` varchar(60) NOT NULL,
  `sexo` char(1) NOT NULL,
  `rg` varchar(12) NOT NULL,
  `senha` varchar(60) NOT NULL,
  `tipoacesso` int(11) NOT NULL DEFAULT '1',
  `ativo` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'44738354809','Funcionario SP','2010-10-10','123','a@a.com','M','321','1234',2,1),(4,'122','Funcionario B','2010-10-10','123','b@b.com','M','333','1234',5,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-25  9:15:38
