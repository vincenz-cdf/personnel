CREATE DATABASE personnel;
use personnel;

DROP TABLE IF EXISTS ligue;
DROP TABLE IF EXISTS employe;

CREATE TABLE ligue(
id int NOT NULL AUTO_INCREMENT,
nom VARCHAR(20),
PRIMARY KEY (id)
)ENGINE = INNODB;

CREATE TABLE employe(
     id_employe int NOT NULL AUTO_INCREMENT, 
     nom_employe VARCHAR(20), 
     prenom_employe VARCHAR(20),
     mail_employe VARCHAR(50),
     password_employe VARCHAR(20),
     date_arrivee DATE,
     date_depart DATE,
     isadmin INTEGER DEFAULT 0,
     isroot INTEGER DEFAULT 0,
     id_ligue int,
     PRIMARY KEY (id_employe)
)ENGINE = INNODB;
