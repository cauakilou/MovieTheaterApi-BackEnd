-- Inserção de Usuários
insert into Users (id, user_email, user_password, role) values(100, 'ana@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_ADMIN');
insert into Users (id, user_email, user_password, role) values(101, 'caua@email.com','$2a$12$DnRGdhJRYfinn5DdgkXbiOZhEdKL.q.nLq0LXciB4yvfVlYAlOSH2','ROLE_CLIENT');

-- Inserção de Filmes
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (10, 'O Exterminador do Futuro 2', 'Um ciborgue T-800 protege o jovem John Connor.', 'poster.jpg', 'DEZESSEIS_ANOS', 8220000000000, 'ACAO', 'EM_CARTAZ');
insert into Movies (id, Name, Sinopse, Poster, Rating, Duration, Gender, Status) values (90, 'Parasita', 'Uma família pobre se infiltra na vida de uma família rica.', 'parasita.jpg', 'DEZESSEIS_ANOS', 7920000000000, 'SUSPENSE', 'EM_CARTAZ');

-- Inserção de Salas
insert into Rooms (id, room_name, model) values (10, 'sala-01', 'PADRAO');
insert into Rooms (id, room_name, model) values (20, 'sala-02', 'IMAX');

-- Inserção de Sessões
-- Sessão 1 para o filme 10 na sala 10
insert into Sessions (id, movie_id, room_id, data, horario, horario_de_termino, status) values (100, 10, 10, '2025-10-20', '20:00:00', '22:17:00', 'A_EXIBIR');
-- Sessão 2 para o filme 90 na sala 20
insert into Sessions (id, movie_id, room_id, data, horario, horario_de_termino, status) values (101, 90, 20, '2025-10-20', '21:30:00', '23:42:00', 'A_EXIBIR');
-- Sessão 3 para o filme 10 na sala 20 (mesmo filme, outra sala e data)
insert into Sessions (id, movie_id, room_id, data, horario, horario_de_termino, status) values (102, 10, 20, '2025-10-21', '19:00:00', '21:17:00', 'A_EXIBIR');