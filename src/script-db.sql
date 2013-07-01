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
  `nome` varchar(200) NOT NULL,
  `senha` varchar(40) NOT NULL,
  `tipoUsuario` varchar(10) DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table endereco(
	id bigint(20) not null auto_increment,
	tipoLogradouro varchar(70) not null,
	logradouro varchar(200) not null,
	complemento varchar(100) null, 
	numero varchar(10) null,
	bairro varchar(50) not null,
	cidade varchar(100) not null, 
	cep varchar(8) not null,
	estado varchar(2) not null,
	primary key(id)
);

create table telefone(
	id bigint(20) not null auto_increment,
	ddd varchar(5) not null,
	numero varchar(10) not null,
	tipoTelefone varchar(15) not null, 
	primary key(id)
);

create table loja(
	id bigint(20) not null auto_increment,
	idUsuario bigint not null,
	idEndereco bigint not null,	
	contato varchar(200) not null,
	cnpj varchar(14) not null, 
	inscricaoEstadual varchar(10) null,
	site varchar(100) null,
	areaInteresse varchar(15) not null, 
	primary key(id)
);

alter table loja add constraint fk_usuario_loja foreign key usuario (idUsuario) references usuario (id);
alter table loja add constraint fk_endereco_loja foreign key endereco (idEndereco) references endereco (id);

create table consumidor(
	id bigint(20) not null auto_increment,
	idUsuario bigint not null,
	idEndereco bigint not null,	 
	primary key(id)
);

alter table consumidor add constraint fk_usuario_consumidor foreign key usuario (idUsuario) references usuario (id);
alter table consumidor add constraint fk_endereco_consumidor foreign key endereco (idEndereco) references endereco (id);

create table loja_telefone(
	loja_id bigint(20) not null,
	telefones_id bigint(20) not null
);

alter table loja_telefone add constraint fk_loja_loja_telefone foreign key loja (loja_id) references loja (id);
alter table loja_telefone add constraint fk_telefone_loja_telefone foreign key telefone (telefones_id) references telefone (id);

create table consumidor_telefone(
	consumidor_id bigint(20) not null,
	telefones_id bigint(20) not null
);

alter table consumidor_telefone add constraint fk_consumidor_consumidor_telefone foreign key consumidor (consumidor_id) references consumidor (id);
alter table consumidor_telefone add constraint fk_telefone_consumidor_telefone foreign key telefone (telefones_id) references telefone (id);

create table banner(
	id bigint(20) not null auto_increment,
	idLoja bigint not null,
	nomeArquivo varchar(255) not null,
	contentType varchar(50) not null, 
	tamanho bigint not null,
	dados mediumblob,
	primary key(id)
);

alter table banner add constraint fk_loja_banner foreign key loja (idLoja) references loja (id);

create table produto(
	id bigint(20) not null auto_increment,
	idLoja bigint(20) not null,
	nome varchar(255) not null,
	fabricante varchar(255) not null,
	modelo varchar(255) not null,
	preco double not null,	
	descricao text not null,
	ano int not null, 
	primary key(id)
);

alter table produto add constraint fk_loja_produto foreign key loja (idLoja) references loja (id);

create table historicopesquisa (
	id bigint(20) not null auto_increment,
	idConsumidor bigint(20) not null,
	nome varchar(255) null,
	fabricante varchar(255) null,
	modelo varchar(255)  null,	
	ano varchar(255) null,
	realizadoEm datetime not null,
	primary key(id)
);

alter table historicopesquisa add constraint fk_consumidor_historicopesquisa foreign key consumidor (idConsumidor) references consumidor (id);

insert into usuario (ativo, dataCriacao, login, nome, senha, tipoUsuario, email) values (true, '2013-01-01', 'admin', 'Administrador do Sistema', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'ROLE_ADMIN', 'contato@hslife.com.br');