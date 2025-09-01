insert into Users (id, user_email, user_password, role)values(100, 'ana@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_ADMIN')
insert into Users (id, user_email, user_password, role)values(101, 'caua@email.com','$2a$12$DnRGdhJRYfinn5DdgkXbiOZhEdKL.q.nLq0LXciB4yvfVlYAlOSH2','ROLE_CLIENT')
insert into Users (id, user_email, user_password, role)values(123, 'sam@email.com','$2a$12$AJmWeuEWvCV1VtRNcS776OclrYrmcoEWw8YTrQVfCo1vC9ak6JVoa','ROLE_CLIENT')
insert into Users (id, user_email, user_password, role)values(102, 'toby@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_CLIENT')

insert into Clients (id,name,cpf,id_User,Phone_Number)values(10, 'Bianca Silva', '97688468019',101,'(11) 98765-4321')
insert into Clients (id,name,cpf,id_User,Phone_Number)values(20, 'Roberto Gomes', '81708991093',123,'21987654321')