ALTER TABLE employe
     ADD CONSTRAINT FK_employe FOREIGN KEY (id_ligue) REFERENCES ligue (id);

INSERT INTO ligue VALUES(1,'yopidou');