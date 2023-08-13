INSERT INTO Autor(id, nome, email) VALUES (1,'Sergio Lopes','sergio.lopes@caelum.com.br');
INSERT INTO Autor(id, nome, email) VALUES (2,'Nico Steppat','nico.steppat@caelum.com.br');
INSERT INTO Autor(id, nome, email) VALUES (3,'Mauricio Aniche','aniche@teste.com.br');
INSERT INTO Autor(id, nome, email) VALUES (4,'Flavio Almeida','flavio.almeida@caelum.com.br');
INSERT INTO Autor(id, nome, email) VALUES (5,'Paulo Silveira','paulo.silveira@caelum.com.br');
INSERT INTO Livro(id, dataLancamento, isbn, preco, titulo) VALUES (1,'2016-02-26','1345663423',49.9,'MEAN');
INSERT INTO Livro(id, dataLancamento, isbn, preco, titulo) VALUES (2,'2016-02-27','199999999999',49.9,'Arquitetura Java');
INSERT INTO Livro_Autor(Livro_id, autores_id) VALUES (1,4),(2,1),(2,2),(2,5);
INSERT INTO Usuario(id, email, senha ) VALUES (1,'nico.steppat@caelum.com.br','12345');
