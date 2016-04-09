EncontreAquiPeças
=================

Este projeto objetiva avaliar a modelagem e implementação de um sistema baseado em web para anúncios de vendas na internet, onde o usuário poderá reunir, comparar e agrupar todas as informações sobre a comercialização de peça(s) automotiva(s), informações estas tais como: marca, fabricante, modelo, preço, vendedor, telefone, localidade, a fim de facilitar a busca e poupar tempo, esforço e deslocamento na sua procura.  A idéia principal do projeto será elaborar um sistema que facilite o consumidor na hora de procurar um produto para comprar e de divulgar um produto nesse caso auxiliando o vendedor.  Este projeto estará focando dois alvos. O consumidor e o vendedor (LOJA).

* Linguagem: Java
* Versão do Java: 1.6 ou 1.7
* Banco de dados: MySQL 5.5 ou superior
* IDE: Eclipse Juno (4.2) JavaEE
* Servidor Java: Tomcat 6.0.36
* Frameworks Java: JSF 2.1.6; PrimeFaces 3.4.1; RichFaces 4.1; Hibernate 4.0.1; Spring 3.1.2; Spring Security 3.1.2
* UML: Astah Community 6.6.4

### Configuração do MySQL:

Após instalar o MySQL, acesse via console ou usando uma ferramenta gráfica de administração e execute os seguintes comandos:

```sql
-- Criação da base de dados
create database encontreaquipecas;

-- Criação do usuário para acessar a base
create user 'encontreaqui'@'localhost' identified by 'p3c4s1@3';
grant all privileges on encontreaquipecas.* to 'encontreaqui'@'localhost' with grant option;
```

Na pasta **src** executar o arquivo **script-db.sql** para criar as tabelas da base.