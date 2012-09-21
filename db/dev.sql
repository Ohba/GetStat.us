-- Create URLS table.
CREATE TABLE urls
( id int NOT NULL PRIMARY KEY
, destinantion varchar(255) NOT NULL
, request varchar(255) NOT NULL
, created date NOT NULL	
);

-- Seed URLS table with dummy data
INSERT INTO urls values (1, 'http://www.google.com', 'test', curdate());