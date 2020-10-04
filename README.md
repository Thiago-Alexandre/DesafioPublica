# DesafioPublica
##Projeto referente a questão prática do desafio Pública.
Se trata de um sistema de acompanhamento de resultados de pontuações em jogos de basquete. Foi desenvolvido utilizando:

* Linguagem Java (JDK 1.8);
* IDE Apache Netbeans 11.3;
* Maven para o gerenciamento de dependências;
* Servidor Apache Tomcat 9.0;
* API JSF 2.2;
* JPA com Hibernate 5.4.17;
* Hibernate Validator 5.4.3;
* PrimeFaces 7.0;
* Banco de Dados MariaDB 10.4;
* JUnit 4.13.

Para executar a aplicação, deverá ser adicionado o arquivo .war (encontrado em \DesafioPublica\target\DesafioPublica-1.0.war) em um servidor (como o Tomcat). Como este projeto foi desenvolvido localmente, será necessário possuir o banco de dados local e configurado conforme o arquivo persistence.xml. As configurações utilizadas no desenvolvimento deste projeto são:

´´´
      <property name="javax.persistence.jdbc.url" value="jdbc:mariadb://localhost:3306/desafio_publica"/> //URL do banco de dados, com o nome do banco criado
      <property name="javax.persistence.jdbc.user" value="root"/> //Usuário configurado para o banco
      <property name="javax.persistence.jdbc.driver" value="org.mariadb.jdbc.Driver"/> //driver de conexão com o banco
      <property name="javax.persistence.jdbc.password" value="thi123"/> //Senha configurada para o banco
´´´
É possível alterar e até configurar uma nova unidade de persistência para conexão através do arquivo persistence.xml.
Para que o JPA possa persistir os dados, é necessário possuir o banco criado. Além do mais, o JPA foi configurado para não alterar a estrutura do banco de dados, permitindo assim ter um maior controle sobre o banco e impedir a criação de tabelas e relacionamentos desnecessários. Portanto, segue a estrutura para a criação do banco de dados, também disponível no arquivo Desafio_Publica.sql:

´´´
DROP DATABASE if EXISTS desafio_publica;
CREATE DATABASE desafio_publica CHARSET latin1 COLLATE latin1_general_cs;
USE desafio_publica;

CREATE TABLE jogador(
	id_jogador INTEGER PRIMARY KEY AUTO_INCREMENT,
	nome_jogador VARCHAR(50) NOT NULL,
	min_jogador INTEGER NOT NULL,
	max_jogador INTEGER NOT NULL,
	quebra_recorde_min_jogador INTEGER NOT NULL,
	quebra_recorde_max_jogador INTEGER NOT NULL,
	login_jogador VARCHAR(30) NOT NULL,
	senha_jogador VARCHAR(30) NOT NULL
);

CREATE TABLE temporada(
	id_temporada INTEGER PRIMARY KEY AUTO_INCREMENT,
	nome_temporada VARCHAR(30) NOT NULL,
	min_temporada INTEGER NOT NULL,
	max_temporada INTEGER NOT NULL,
	jogador_temporada INTEGER NOT NULL,
	FOREIGN KEY (jogador_temporada) REFERENCES jogador(id_jogador) ON DELETE CASCADE
);

CREATE TABLE jogo (
	id_jogo INTEGER PRIMARY KEY AUTO_INCREMENT,
	data_jogo DATETIME NOT NULL,
	descricao_jogo VARCHAR(250) NOT NULL,
	placar_jogo INTEGER NOT NULL,
	temporada_jogo INTEGER NOT NULL,
	FOREIGN KEY (temporada_jogo) REFERENCES temporada(id_temporada) ON DELETE CASCADE
);
´´´
Caso exista o interesse de testar a aplicação sem configurar localmente, é possível disponibilizar um link seguro, utilizando a ferramenta Ngrok, para o acesso do sistema rodando diretamente no seu ambiente de desenvolvimento. Para mais detalhes, envie um e-mail solicitando essa abordagem para: thiago.alexandre.new@gmail.com
