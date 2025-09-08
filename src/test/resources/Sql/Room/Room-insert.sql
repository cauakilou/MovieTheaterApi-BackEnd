insert into Users (id, user_email, user_password, role)values(100, 'ana@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_ADMIN');
insert into Users (id, user_email, user_password, role)values(101, 'caua@email.com','$2a$12$DnRGdhJRYfinn5DdgkXbiOZhEdKL.q.nLq0LXciB4yvfVlYAlOSH2','ROLE_CLIENT');
insert into Users (id, user_email, user_password, role)values(123, 'sam@email.com','$2a$12$AJmWeuEWvCV1VtRNcS776OclrYrmcoEWw8YTrQVfCo1vC9ak6JVoa','ROLE_CLIENT');
insert into Users (id, user_email, user_password, role)values(102, 'toby@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_CLIENT');

insert into Clients (id,name,cpf,id_User,Phone_Number)values(10, 'Bianca Silva', '97688468019',101,'(11) 98765-4321');
insert into Clients (id,name,cpf,id_User,Phone_Number)values(20, 'Roberto Gomes', '81708991093',123,'21987654321');

-- Adição das Salas e Assentos com IDs atualizados

-- Sala 1: PADRAO (ID 10)
insert into Rooms (id, room_name, model) values (10, 'sala-01', 'PADRAO');
-- Fileiras A-E, Assentos começando em 100
insert into Seats (id, row_identifier, column_identifier, seat_type, room_id) values
(100, 'A', 1, 'PADRAO', 10), (101, 'A', 2, 'PADRAO', 10), (102, 'A', 3, 'PADRAO', 10), (103, 'A', 4, 'PADRAO', 10), (104, 'A', 5, 'PADRAO', 10), (105, 'A', 6, 'PADRAO', 10), (106, 'A', 7, 'PADRAO', 10), (107, 'A', 8, 'PADRAO', 10),
(108, 'B', 1, 'PADRAO', 10), (109, 'B', 2, 'PADRAO', 10), (110, 'B', 3, 'PADRAO', 10), (111, 'B', 4, 'PADRAO', 10), (112, 'B', 5, 'PADRAO', 10), (113, 'B', 6, 'PADRAO', 10), (114, 'B', 7, 'PADRAO', 10), (115, 'B', 8, 'PADRAO', 10),
(116, 'C', 1, 'PADRAO', 10), (117, 'C', 2, 'PADRAO', 10), (118, 'C', 3, 'PADRAO', 10), (119, 'C', 4, 'PADRAO', 10), (120, 'C', 5, 'PADRAO', 10), (121, 'C', 6, 'PADRAO', 10), (122, 'C', 7, 'PADRAO', 10), (123, 'C', 8, 'PADRAO', 10),
(124, 'D', 1, 'PADRAO', 10), (125, 'D', 2, 'PADRAO', 10), (126, 'D', 3, 'PADRAO', 10), (127, 'D', 4, 'PADRAO', 10), (128, 'D', 5, 'PADRAO', 10), (129, 'D', 6, 'PADRAO', 10), (130, 'D', 7, 'PADRAO', 10), (131, 'D', 8, 'PADRAO', 10),
(132, 'E', 1, 'PADRAO', 10), (133, 'E', 2, 'PADRAO', 10), (134, 'E', 3, 'PADRAO', 10), (135, 'E', 4, 'PADRAO', 10), (136, 'E', 5, 'ACESSIBILIDADE', 10), (137, 'E', 6, 'ACESSIBILIDADE', 10), (138, 'E', 7, 'PADRAO', 10), (139, 'E', 8, 'PADRAO', 10);

-- Sala 2: IMAX (ID 20)
insert into Rooms (id, room_name, model) values (20, 'sala-02', 'IMAX');
-- Fileiras A-B, Assentos continuando a partir do último ID
insert into Seats (id, row_identifier, column_identifier, seat_type, room_id) values
(140, 'A', 1, 'PADRAO', 20), (141, 'A', 2, 'PADRAO', 20), (142, 'A', 3, 'PADRAO', 20), (143, 'A', 4, 'PADRAO', 20), (144, 'A', 5, 'PADRAO', 20), (145, 'A', 6, 'PADRAO', 20), (146, 'A', 7, 'PADRAO', 20), (147, 'A', 8, 'PADRAO', 20), (148, 'A', 9, 'PADRAO', 20), (149, 'A', 10, 'PADRAO', 20), (150, 'A', 11, 'PADRAO', 20), (151, 'A', 12, 'PADRAO', 20),
(152, 'B', 1, 'PADRAO', 20), (153, 'B', 2, 'PADRAO', 20), (154, 'B', 3, 'PADRAO', 20), (155, 'B', 4, 'PADRAO', 20), (156, 'B', 5, 'PADRAO', 20), (157, 'B', 6, 'PADRAO', 20), (158, 'B', 7, 'PADRAO', 20), (159, 'B', 8, 'PADRAO', 20), (160, 'B', 9, 'PADRAO', 20), (161, 'B', 10, 'PADRAO', 20), (162, 'B', 11, 'PADRAO', 20), (163, 'B', 12, 'PADRAO', 20);
-- ... (Continue para as outras fileiras da sala 2 se necessário)

-- Sala 3: VIP (ID 30)
insert into Rooms (id, room_name, model) values (30, 'sala-03', 'VIP');
-- Fileiras A-D, Assentos continuando a partir do último ID
insert into Seats (id, row_identifier, column_identifier, seat_type, room_id) values
(164, 'A', 1, 'PADRAO', 30), (165, 'A', 2, 'PADRAO', 30), (166, 'A', 3, 'NAMORADEIRA', 30), (167, 'A', 4, 'NAMORADEIRA', 30), (168, 'A', 5, 'PADRAO', 30), (169, 'A', 6, 'PADRAO', 30),
(170, 'B', 1, 'PADRAO', 30), (171, 'B', 2, 'PADRAO', 30), (172, 'B', 3, 'PADRAO', 30), (173, 'B', 4, 'PADRAO', 30), (174, 'B', 5, 'PADRAO', 30), (175, 'B', 6, 'PADRAO', 30),
(176, 'C', 1, 'PADRAO', 30), (177, 'C', 2, 'PADRAO', 30), (178, 'C', 3, 'PADRAO', 30), (179, 'C', 4, 'PADRAO', 30), (180, 'C', 5, 'PADRAO', 30), (181, 'C', 6, 'PADRAO', 30),
(182, 'D', 1, 'ACESSIBILIDADE', 30), (183, 'D', 2, 'PADRAO', 30), (184, 'D', 3, 'PADRAO', 30), (185, 'D', 4, 'PADRAO', 30), (186, 'D', 5, 'PADRAO', 30), (187, 'D', 6, 'ACESSIBILIDADE', 30);