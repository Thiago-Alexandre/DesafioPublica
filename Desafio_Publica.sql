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