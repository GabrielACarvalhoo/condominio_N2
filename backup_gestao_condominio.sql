-- MySQL dump 10.13  Distrib 8.4.13, for Linux (x86_64)
--
-- Host: localhost    Database: gestao_condominio
-- ------------------------------------------------------
-- Server version	8.4.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `autorizacao_visitante`
--

DROP TABLE IF EXISTS `autorizacao_visitante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autorizacao_visitante` (
  `id_autorizacao` int NOT NULL AUTO_INCREMENT,
  `id_visitante` int NOT NULL,
  `id_unidade` int NOT NULL,
  `id_usuario_autorizador` int NOT NULL,
  `inicio_validade` datetime NOT NULL,
  `fim_validade` datetime DEFAULT NULL,
  `quantidade_acessos` int DEFAULT NULL,
  `status` enum('PENDENTE','APROVADA','CANCELADA','EXPIRADA') NOT NULL DEFAULT 'PENDENTE',
  `observacoes` text,
  PRIMARY KEY (`id_autorizacao`),
  KEY `fk_av_visitante` (`id_visitante`),
  KEY `fk_av_unidade` (`id_unidade`),
  KEY `fk_av_autorizador` (`id_usuario_autorizador`),
  KEY `idx_av_periodo` (`inicio_validade`,`fim_validade`),
  CONSTRAINT `fk_av_autorizador` FOREIGN KEY (`id_usuario_autorizador`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `fk_av_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`),
  CONSTRAINT `fk_av_visitante` FOREIGN KEY (`id_visitante`) REFERENCES `visitante` (`id_visitante`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autorizacao_visitante`
--

LOCK TABLES `autorizacao_visitante` WRITE;
/*!40000 ALTER TABLE `autorizacao_visitante` DISABLE KEYS */;
INSERT INTO `autorizacao_visitante` VALUES (2,2,6,7,'2009-09-24 17:25:00','2026-11-30 23:59:00',1,'APROVADA',NULL);
/*!40000 ALTER TABLE `autorizacao_visitante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cargo` (
  `id_cargo` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cargo`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (1,'Porteiro','Controle de acesso e recebimento de encomendas'),(2,'Zelador','Conservacao e funcionamento do condominio'),(3,'Auxiliar de Servicos Gerais','Limpeza e conservacao'),(4,'Responsavel pela Manutencao','Reparos e manutencao');
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comunicado`
--

DROP TABLE IF EXISTS `comunicado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunicado` (
  `id_comunicado` int NOT NULL AUTO_INCREMENT,
  `id_usuario_autor` int NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `conteudo` text NOT NULL,
  `publicado_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `inicio_exibicao` datetime DEFAULT NULL,
  `fim_exibicao` datetime DEFAULT NULL,
  `status` enum('RASCUNHO','PUBLICADO','ARQUIVADO') NOT NULL DEFAULT 'RASCUNHO',
  PRIMARY KEY (`id_comunicado`),
  KEY `fk_com_usuario` (`id_usuario_autor`),
  CONSTRAINT `fk_com_usuario` FOREIGN KEY (`id_usuario_autor`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunicado`
--

LOCK TABLES `comunicado` WRITE;
/*!40000 ALTER TABLE `comunicado` DISABLE KEYS */;
/*!40000 ALTER TABLE `comunicado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `despesa`
--

DROP TABLE IF EXISTS `despesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `despesa` (
  `id_despesa` int NOT NULL AUTO_INCREMENT,
  `id_usuario_registro` int NOT NULL,
  `descricao` varchar(180) NOT NULL,
  `categoria` varchar(80) NOT NULL,
  `fornecedor` varchar(120) DEFAULT NULL,
  `valor` decimal(10,2) NOT NULL,
  `data_despesa` date NOT NULL,
  `vencimento` date DEFAULT NULL,
  `paga` tinyint(1) NOT NULL DEFAULT '0',
  `paga_em` date DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`id_despesa`),
  KEY `fk_desp_usuario` (`id_usuario_registro`),
  CONSTRAINT `fk_desp_usuario` FOREIGN KEY (`id_usuario_registro`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `despesa`
--

LOCK TABLES `despesa` WRITE;
/*!40000 ALTER TABLE `despesa` DISABLE KEYS */;
/*!40000 ALTER TABLE `despesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento` (
  `id_documento` int NOT NULL AUTO_INCREMENT,
  `id_usuario_responsavel` int NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `descricao` text,
  `categoria` varchar(80) DEFAULT NULL,
  `caminho_arquivo` varchar(500) NOT NULL,
  `enviado_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `visibilidade` enum('TODOS','MORADORES','ADMINISTRACAO') NOT NULL DEFAULT 'TODOS',
  PRIMARY KEY (`id_documento`),
  KEY `fk_doc_usuario` (`id_usuario_responsavel`),
  CONSTRAINT `fk_doc_usuario` FOREIGN KEY (`id_usuario_responsavel`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encomenda`
--

DROP TABLE IF EXISTS `encomenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encomenda` (
  `id_encomenda` int NOT NULL AUTO_INCREMENT,
  `id_unidade` int NOT NULL,
  `id_funcionario_recebimento` int DEFAULT NULL,
  `descricao` varchar(255) NOT NULL,
  `transportadora` varchar(100) DEFAULT NULL,
  `codigo_rastreio` varchar(100) DEFAULT NULL,
  `recebida_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `retirada_em` datetime DEFAULT NULL,
  `retirado_por` varchar(120) DEFAULT NULL,
  `status` enum('RECEBIDA','RETIRADA','DEVOLVIDA') NOT NULL DEFAULT 'RECEBIDA',
  `observacoes` text,
  PRIMARY KEY (`id_encomenda`),
  KEY `fk_encomenda_unidade` (`id_unidade`),
  KEY `fk_encomenda_func` (`id_funcionario_recebimento`),
  CONSTRAINT `fk_encomenda_func` FOREIGN KEY (`id_funcionario_recebimento`) REFERENCES `funcionario` (`id_funcionario`),
  CONSTRAINT `fk_encomenda_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encomenda`
--

LOCK TABLES `encomenda` WRITE;
/*!40000 ALTER TABLE `encomenda` DISABLE KEYS */;
INSERT INTO `encomenda` VALUES (1,6,5,'Pacote avantajado ','Brass','FKDFMSAKFD','2026-11-11 19:11:14','2026-11-11 16:16:10','Matheus ','RETIRADA','NADA');
/*!40000 ALTER TABLE `encomenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `espaco_comum`
--

DROP TABLE IF EXISTS `espaco_comum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espaco_comum` (
  `id_espaco` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` text,
  `capacidade` int NOT NULL,
  `regras` text,
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_espaco`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espaco_comum`
--

LOCK TABLES `espaco_comum` WRITE;
/*!40000 ALTER TABLE `espaco_comum` DISABLE KEYS */;
INSERT INTO `espaco_comum` VALUES (1,'Churrasqueira','Área com churrasqueira para moradores',20,'Reservar com antecedência',1),(2,'Sala de Jogos ','Sala com jogos ',8,'Não quebrar os jogos',1);
/*!40000 ALTER TABLE `espaco_comum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
  `id_funcionario` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_cargo` int NOT NULL,
  `data_admissao` date DEFAULT NULL,
  `data_demissao` date DEFAULT NULL,
  PRIMARY KEY (`id_funcionario`),
  UNIQUE KEY `id_usuario` (`id_usuario`),
  KEY `fk_func_cargo` (`id_cargo`),
  CONSTRAINT `fk_func_cargo` FOREIGN KEY (`id_cargo`) REFERENCES `cargo` (`id_cargo`),
  CONSTRAINT `fk_func_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (4,7,2,'2007-04-28',NULL),(5,5,1,'2024-11-11',NULL);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrencia`
--

DROP TABLE IF EXISTS `ocorrencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocorrencia` (
  `id_ocorrencia` int NOT NULL AUTO_INCREMENT,
  `id_unidade` int DEFAULT NULL,
  `id_usuario_registro` int NOT NULL,
  `id_funcionario_responsavel` int DEFAULT NULL,
  `categoria` enum('PORTARIA','SEGURANCA','CONVIVENCIA','INFRAESTRUTURA','OUTRA') NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `descricao` text NOT NULL,
  `registrada_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('ABERTA','EM_ANALISE','RESOLVIDA','CANCELADA') NOT NULL DEFAULT 'ABERTA',
  PRIMARY KEY (`id_ocorrencia`),
  KEY `fk_oc_unidade` (`id_unidade`),
  KEY `fk_oc_usuario` (`id_usuario_registro`),
  KEY `fk_oc_func` (`id_funcionario_responsavel`),
  CONSTRAINT `fk_oc_func` FOREIGN KEY (`id_funcionario_responsavel`) REFERENCES `funcionario` (`id_funcionario`),
  CONSTRAINT `fk_oc_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`),
  CONSTRAINT `fk_oc_usuario` FOREIGN KEY (`id_usuario_registro`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrencia`
--

LOCK TABLES `ocorrencia` WRITE;
/*!40000 ALTER TABLE `ocorrencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagamento` (
  `id_pagamento` int NOT NULL AUTO_INCREMENT,
  `id_taxa` int NOT NULL,
  `valor_pago` decimal(10,2) NOT NULL,
  `pago_em` datetime NOT NULL,
  `forma_pagamento` enum('PIX','BOLETO','DINHEIRO','CARTAO','TRANSFERENCIA','OUTRA') NOT NULL,
  `comprovante` varchar(500) DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`id_pagamento`),
  KEY `fk_pag_taxa` (`id_taxa`),
  CONSTRAINT `fk_pag_taxa` FOREIGN KEY (`id_taxa`) REFERENCES `taxa_condominial` (`id_taxa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
INSERT INTO `pagamento` VALUES (2,1,120.00,'2026-11-13 12:57:44','PIX',NULL,'Segundo pagamento de teste'),(5,3,200.00,'2026-11-13 13:38:37','PIX','nao','nao');
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id_perfil` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'PROPRIETARIO','Proprietario de unidade'),(2,'INQUILINO','Morador de unidade alugada'),(3,'SINDICO','Administracao geral do condominio'),(4,'SUBSINDICO','Auxilia a administracao'),(5,'PORTEIRO','Controle de acesso e encomendas'),(6,'ZELADOR','Conservacao e operacao'),(7,'AUXILIAR_SERVICOS_GERAIS','Limpeza e conservacao'),(8,'RESPONSAVEL_MANUTENCAO','Execucao e acompanhamento de reparos');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_acesso`
--

DROP TABLE IF EXISTS `registro_acesso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_acesso` (
  `id_acesso` int NOT NULL AUTO_INCREMENT,
  `id_autorizacao` int NOT NULL,
  `id_funcionario_portaria` int DEFAULT NULL,
  `entrada` datetime NOT NULL,
  `saida` datetime DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`id_acesso`),
  KEY `fk_acesso_autorizacao` (`id_autorizacao`),
  KEY `fk_acesso_funcionario` (`id_funcionario_portaria`),
  CONSTRAINT `fk_acesso_autorizacao` FOREIGN KEY (`id_autorizacao`) REFERENCES `autorizacao_visitante` (`id_autorizacao`),
  CONSTRAINT `fk_acesso_funcionario` FOREIGN KEY (`id_funcionario_portaria`) REFERENCES `funcionario` (`id_funcionario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_acesso`
--

LOCK TABLES `registro_acesso` WRITE;
/*!40000 ALTER TABLE `registro_acesso` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_acesso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `id_espaco` int NOT NULL,
  `id_usuario_solicitante` int NOT NULL,
  `inicio` datetime NOT NULL,
  `fim` datetime NOT NULL,
  `quantidade_pessoas` int DEFAULT NULL,
  `status` enum('PENDENTE','APROVADA','REJEITADA','CANCELADA','CONCLUIDA') NOT NULL DEFAULT 'PENDENTE',
  `id_usuario_aprovador` int DEFAULT NULL,
  `solicitada_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `observacoes` text,
  PRIMARY KEY (`id_reserva`),
  KEY `fk_res_solicitante` (`id_usuario_solicitante`),
  KEY `fk_res_aprovador` (`id_usuario_aprovador`),
  KEY `idx_res_espaco_periodo` (`id_espaco`,`inicio`,`fim`),
  CONSTRAINT `fk_res_aprovador` FOREIGN KEY (`id_usuario_aprovador`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `fk_res_espaco` FOREIGN KEY (`id_espaco`) REFERENCES `espaco_comum` (`id_espaco`),
  CONSTRAINT `fk_res_solicitante` FOREIGN KEY (`id_usuario_solicitante`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `chk_res_periodo` CHECK ((`fim` > `inicio`))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servico_manutencao`
--

DROP TABLE IF EXISTS `servico_manutencao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servico_manutencao` (
  `id_servico` int NOT NULL AUTO_INCREMENT,
  `id_solicitacao` int NOT NULL,
  `id_funcionario_executor` int DEFAULT NULL,
  `descricao` text NOT NULL,
  `realizado_em` datetime NOT NULL,
  `custo` decimal(10,2) DEFAULT '0.00',
  `observacoes` text,
  PRIMARY KEY (`id_servico`),
  KEY `fk_serv_solicitacao` (`id_solicitacao`),
  KEY `fk_serv_func` (`id_funcionario_executor`),
  CONSTRAINT `fk_serv_func` FOREIGN KEY (`id_funcionario_executor`) REFERENCES `funcionario` (`id_funcionario`),
  CONSTRAINT `fk_serv_solicitacao` FOREIGN KEY (`id_solicitacao`) REFERENCES `solicitacao_manutencao` (`id_solicitacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servico_manutencao`
--

LOCK TABLES `servico_manutencao` WRITE;
/*!40000 ALTER TABLE `servico_manutencao` DISABLE KEYS */;
/*!40000 ALTER TABLE `servico_manutencao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacao_manutencao`
--

DROP TABLE IF EXISTS `solicitacao_manutencao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitacao_manutencao` (
  `id_solicitacao` int NOT NULL AUTO_INCREMENT,
  `id_usuario_solicitante` int NOT NULL,
  `id_unidade` int DEFAULT NULL,
  `id_funcionario_responsavel` int DEFAULT NULL,
  `local_problema` varchar(150) NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `descricao` text NOT NULL,
  `prioridade` enum('BAIXA','MEDIA','ALTA','URGENTE') NOT NULL DEFAULT 'MEDIA',
  `status` enum('ABERTA','EM_ANDAMENTO','AGUARDANDO','CONCLUIDA','CANCELADA') NOT NULL DEFAULT 'ABERTA',
  `aberta_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `concluida_em` datetime DEFAULT NULL,
  PRIMARY KEY (`id_solicitacao`),
  KEY `fk_sm_usuario` (`id_usuario_solicitante`),
  KEY `fk_sm_unidade` (`id_unidade`),
  KEY `fk_sm_func` (`id_funcionario_responsavel`),
  CONSTRAINT `fk_sm_func` FOREIGN KEY (`id_funcionario_responsavel`) REFERENCES `funcionario` (`id_funcionario`),
  CONSTRAINT `fk_sm_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`),
  CONSTRAINT `fk_sm_usuario` FOREIGN KEY (`id_usuario_solicitante`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacao_manutencao`
--

LOCK TABLES `solicitacao_manutencao` WRITE;
/*!40000 ALTER TABLE `solicitacao_manutencao` DISABLE KEYS */;
INSERT INTO `solicitacao_manutencao` VALUES (1,2,NULL,4,'Área da piscina','Problema na iluminação','Uma das luminárias da piscina não está funcionando.','MEDIA','AGUARDANDO','2026-11-13 15:30:12',NULL);
/*!40000 ALTER TABLE `solicitacao_manutencao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxa_condominial`
--

DROP TABLE IF EXISTS `taxa_condominial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taxa_condominial` (
  `id_taxa` int NOT NULL AUTO_INCREMENT,
  `id_unidade` int NOT NULL,
  `descricao` varchar(150) NOT NULL,
  `competencia` char(7) NOT NULL COMMENT 'Formato YYYY-MM',
  `valor` decimal(10,2) NOT NULL,
  `vencimento` date NOT NULL,
  `status` enum('ABERTA','PAGA','VENCIDA','CANCELADA','PARCIAL') NOT NULL DEFAULT 'ABERTA',
  PRIMARY KEY (`id_taxa`),
  UNIQUE KEY `uk_taxa_unidade_competencia_desc` (`id_unidade`,`competencia`,`descricao`),
  CONSTRAINT `fk_taxa_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxa_condominial`
--

LOCK TABLES `taxa_condominial` WRITE;
/*!40000 ALTER TABLE `taxa_condominial` DISABLE KEYS */;
INSERT INTO `taxa_condominial` VALUES (1,2,'Sla 21','2026-07',210.00,'2027-09-10','PARCIAL'),(3,6,'sla','2026-10',200.00,'2027-01-10','PAGA');
/*!40000 ALTER TABLE `taxa_condominial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade`
--

DROP TABLE IF EXISTS `unidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidade` (
  `id_unidade` int NOT NULL AUTO_INCREMENT,
  `bloco` varchar(30) DEFAULT NULL,
  `numero` varchar(20) NOT NULL,
  `andar` int DEFAULT NULL,
  `metragem` decimal(8,2) DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`id_unidade`),
  UNIQUE KEY `uk_unidade_bloco_numero` (`bloco`,`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade`
--

LOCK TABLES `unidade` WRITE;
/*!40000 ALTER TABLE `unidade` DISABLE KEYS */;
INSERT INTO `unidade` VALUES (2,'A','101',1,65.50,'Unidade de teste'),(5,'B','102',1,90.00,'sedsasd'),(6,'B','51',5,100.00,'cliente bradesco  ');
/*!40000 ALTER TABLE `unidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `id_perfil` int NOT NULL,
  `nome` varchar(120) NOT NULL,
  `cpf` char(11) DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `senha_hash` varchar(255) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  `criado_em` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `cpf` (`cpf`),
  KEY `fk_usuario_perfil` (`id_perfil`),
  CONSTRAINT `fk_usuario_perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (2,1,'Joao Teste','12345678901','joao.teste@condominio.com','61999999999','hash_teste_123',1,'2026-11-10 14:13:19'),(5,3,'Gabriel','07102695101','gabrielcarv205@gmail.com','982693895','hash_demo',1,'2026-11-10 17:14:43'),(7,2,'Guilherme ','99900011101','guilherme@gmail.com','988899998','hash_demo',1,'2026-11-10 17:21:00');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_unidade`
--

DROP TABLE IF EXISTS `usuario_unidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_unidade` (
  `id_usuario_unidade` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_unidade` int NOT NULL,
  `tipo_vinculo` enum('PROPRIETARIO','INQUILINO','MORADOR') NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date DEFAULT NULL,
  `responsavel_financeiro` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_usuario_unidade`),
  KEY `idx_uu_unidade` (`id_unidade`),
  KEY `idx_uu_usuario` (`id_usuario`),
  CONSTRAINT `fk_uu_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`),
  CONSTRAINT `fk_uu_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `chk_uu_datas` CHECK (((`data_fim` is null) or (`data_fim` >= `data_inicio`)))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_unidade`
--

LOCK TABLES `usuario_unidade` WRITE;
/*!40000 ALTER TABLE `usuario_unidade` DISABLE KEYS */;
INSERT INTO `usuario_unidade` VALUES (2,7,5,'INQUILINO','2026-11-10',NULL,1);
/*!40000 ALTER TABLE `usuario_unidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaga`
--

DROP TABLE IF EXISTS `vaga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaga` (
  `id_vaga` int NOT NULL AUTO_INCREMENT,
  `id_unidade` int DEFAULT NULL,
  `identificacao` varchar(30) NOT NULL,
  `tipo` enum('CARRO','MOTO','BICICLETA','OUTRO') NOT NULL DEFAULT 'CARRO',
  `coberta` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('DISPONIVEL','VINCULADA','MANUTENCAO') NOT NULL DEFAULT 'DISPONIVEL',
  PRIMARY KEY (`id_vaga`),
  UNIQUE KEY `identificacao` (`identificacao`),
  KEY `fk_vaga_unidade` (`id_unidade`),
  CONSTRAINT `fk_vaga_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaga`
--

LOCK TABLES `vaga` WRITE;
/*!40000 ALTER TABLE `vaga` DISABLE KEYS */;
INSERT INTO `vaga` VALUES (1,NULL,'VAGA-TESTE-01','CARRO',1,'VINCULADA'),(2,6,'Vaga teste 101','CARRO',1,'DISPONIVEL'),(3,6,'vaga x','CARRO',1,'DISPONIVEL');
/*!40000 ALTER TABLE `vaga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veiculo` (
  `id_veiculo` int NOT NULL AUTO_INCREMENT,
  `id_unidade` int NOT NULL,
  `id_usuario` int DEFAULT NULL,
  `placa` varchar(10) NOT NULL,
  `modelo` varchar(80) DEFAULT NULL,
  `marca` varchar(60) DEFAULT NULL,
  `cor` varchar(30) DEFAULT NULL,
  `tipo` enum('CARRO','MOTO','BICICLETA','OUTRO') NOT NULL DEFAULT 'CARRO',
  `id_vaga` int DEFAULT NULL,
  PRIMARY KEY (`id_veiculo`),
  UNIQUE KEY `placa` (`placa`),
  KEY `fk_veiculo_unidade` (`id_unidade`),
  KEY `fk_veiculo_usuario` (`id_usuario`),
  KEY `fk_veiculo_vaga` (`id_vaga`),
  CONSTRAINT `fk_veiculo_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id_unidade`),
  CONSTRAINT `fk_veiculo_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `fk_veiculo_vaga` FOREIGN KEY (`id_vaga`) REFERENCES `vaga` (`id_vaga`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculo`
--

LOCK TABLES `veiculo` WRITE;
/*!40000 ALTER TABLE `veiculo` DISABLE KEYS */;
INSERT INTO `veiculo` VALUES (1,2,NULL,'ABC1D23','Onix','Chevrolet','Prata','CARRO',1),(2,6,NULL,'fdfs1','Prisma','Chevrolet','Cinza','CARRO',NULL);
/*!40000 ALTER TABLE `veiculo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitante`
--

DROP TABLE IF EXISTS `visitante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitante` (
  `id_visitante` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(120) NOT NULL,
  `documento` varchar(30) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `observacoes` text,
  PRIMARY KEY (`id_visitante`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitante`
--

LOCK TABLES `visitante` WRITE;
/*!40000 ALTER TABLE `visitante` DISABLE KEYS */;
INSERT INTO `visitante` VALUES (2,'Jonas ','90909090921','999999999','Barulho'),(3,'Paulo','09092112331','61999961','nada'),(4,'Pedro ','59003113101','909009090','nada de mais');
/*!40000 ALTER TABLE `visitante` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-17 14:56:18
