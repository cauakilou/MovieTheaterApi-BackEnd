insert into Users (id, user_email, user_password, role)values(100, 'ana@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_ADMIN');
insert into Users (id, user_email, user_password, role)values(101, 'caua@email.com','$2a$12$DnRGdhJRYfinn5DdgkXbiOZhEdKL.q.nLq0LXciB4yvfVlYAlOSH2','ROLE_CLIENT');
insert into Users (id, user_email, user_password, role)values(123, 'sam@email.com','$2a$12$AJmWeuEWvCV1VtRNcS776OclrYrmcoEWw8YTrQVfCo1vC9ak6JVoa','ROLE_CLIENT');
insert into Users (id, user_email, user_password, role)values(102, 'toby@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_CLIENT');

insert into Clients (id,name,cpf,id_User,Phone_Number)values(10, 'Bianca Silva', '97688468019',101,'(11) 98765-4321');
insert into Clients (id,name,cpf,id_User,Phone_Number)values(20, 'Roberto Gomes', '81708991093',123,'21987654321');

insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (10, 'O Exterminador do Futuro 2', 'Um ciborgue T-800 protege o jovem John Connor de um novo e avançado ciborgue, o T-1000.', 'https://image.tmdb.org/t/p/original/v4W0V61T2PY4c21NEGKiC0iQDo.jpg', 'DEZESSEIS_ANOS', 8220000000000, 'ACAO', 'EM_CARTAZ');

-- 2. Fantasia / Aventura
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (20, 'O Senhor dos Anéis: A Sociedade do Anel', 'Um hobbit herda um anel mágico e deve embarcar em uma jornada para destruí-lo antes que caia nas mãos do mal.', 'https://image.tmdb.org/t/p/original/u5ySps5GzvoM6nE0gD22mI2a4Z4.jpg', 'DOZE_ANOS', 10680000000000, 'FANTASIA', 'EM_CARTAZ');

-- 3. Drama / Biografia
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (30, 'A Teoria de Tudo', 'A história de Stephen Hawking e sua esposa Jane, que lutaram juntos contra a doença e os desafios da física.', 'https://image.tmdb.org/t/p/original/iR0r4sR2kSSTa3s61W2f23dl8gC.jpg', 'LIVRE', 7380000000000, 'DRAMA', 'FORA_DE_CARTAZ');

-- 4. Terror Psicológico
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (40, 'O Iluminado', 'Um escritor aceita um trabalho como zelador de inverno em um hotel isolado, onde forças sinistras o levam à loucura.', 'https://image.tmdb.org/t/p/original/3hvt42TpHYp4I9s6o2a222aGkgm.jpg', 'DEZESSEIS_ANOS', 8760000000000, 'TERROR', 'EM_CARTAZ');

-- 5. Animação / Comédia
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (50, 'Toy Story', 'O boneco Woody se sente ameaçado quando um novo e moderno brinquedo, Buzz Lightyear, se torna o favorito de seu dono.', 'https://image.tmdb.org/t/p/original/8kI0Ahv6yS9iI12sI2nMSd8s0lD.jpg', 'LIVRE', 4860000000000, 'ANIMACAO', 'EM_BREVE');

-- 6. Ficção Científica / Noir
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (60, 'Blade Runner 2049', 'Um novo blade runner descobre um segredo há muito enterrado que tem o potencial de mergulhar a sociedade no caos.', 'https://image.tmdb.org/t/p/original/gajva2L0rPYkEWjzgFlBwBfC0w5.jpg', 'DEZESSEIS_ANOS', 9780000000000, 'FICCAO_CIENTIFICA', 'EM_CARTAZ');

-- 7. Ação / Pós-apocalíptico
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (70, 'Mad Max: Estrada da Fúria', 'Em um futuro pós-apocalíptico, uma mulher se rebela contra um líder tirano em busca de sua terra natal com a ajuda de Max.', 'https://image.tmdb.org/t/p/original/8tZYtuWezp8kY1FWMvMoSy2Igr.jpg', 'DEZESSEIS_ANOS', 7200000000000, 'ACAO', 'EM_CARTAZ');

-- 8. Comédia Nacional
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (80, 'Minha Mãe é uma Peça 3', 'Dona Hermínia precisa se reinventar e lidar com os novos desafios de ser avó, sogra e mãe de filhos adultos.', 'https://image.tmdb.org/t/p/original/5sLr1H1Ixp1VNj7USHV5c4lA8oV.jpg', 'DOZE_ANOS', 6660000000000, 'COMEDIA', 'EM_BREVE');

-- 9. Suspense / Drama (Vencedor do Oscar)
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (90, 'Parasita', 'Uma família pobre se infiltra na vida de uma família rica, dando início a uma complexa relação de dependência mútua.', 'https://image.tmdb.org/t/p/original/igw9uvxza1IClEVa5K2aYf4n1jS.jpg', 'DEZESSEIS_ANOS', 7920000000000, 'SUSPENSE', 'EM_CARTAZ');

-- 10. Romance / Clássico
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (100, 'Titanic', 'Um romance proibido floresce entre uma jovem da alta sociedade e um artista pobre a bordo do malfadado RMS Titanic.', 'https://image.tmdb.org/t/p/original/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg', 'DOZE_ANOS', 11640000000000, 'ROMANCE', 'FORA_DE_CARTAZ');