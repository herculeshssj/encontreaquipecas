/*** Script de criação da base de dados ***/

CREATE TABLE `auditoria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `classe` varchar(255) NOT NULL,
  `data` date NOT NULL,
  `dataHora` datetime NOT NULL,
  `ip` varchar(255) NOT NULL,
  `transacao` varchar(255) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `auditoriadados` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nomeAtributo` varchar(255) NOT NULL,
  `situacaoOperacao` varchar(255) NOT NULL,
  `valorAtributo` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `auditoria_auditoriadados` (
  `auditoria_id` bigint(20) NOT NULL,
  `dadosAuditoria_id` bigint(20) NOT NULL,
  UNIQUE KEY `dadosAuditoria_id` (`dadosAuditoria_id`),
  KEY `FKF2BF5F9E8841B637` (`dadosAuditoria_id`),
  KEY `FKF2BF5F9E7D80A527` (`auditoria_id`),
  CONSTRAINT `FKF2BF5F9E7D80A527` FOREIGN KEY (`auditoria_id`) REFERENCES `auditoria` (`id`),
  CONSTRAINT `FKF2BF5F9E8841B637` FOREIGN KEY (`dadosAuditoria_id`) REFERENCES `auditoriadados` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ativo` tinyint(1) DEFAULT NULL,
  `dataCriacao` datetime DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `senha` varchar(40) NOT NULL,
  `tipoUsuario` varchar(10) DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

