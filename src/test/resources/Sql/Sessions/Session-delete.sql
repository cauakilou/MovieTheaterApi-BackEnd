-- Deleta dados em ordem para evitar conflitos de chave estrangeira
DELETE FROM Sessions;
DELETE FROM Seats;
DELETE FROM Rooms;
DELETE FROM Movies;
DELETE FROM Clients;
DELETE FROM Users;