-- Create URLS table.
CREATE TABLE urls
( id int NOT NULL PRIMARY KEY AUTO_INCREMENT
, user_id in NOT NULL
, destination varchar(255) NOT NULL
, short varchar(255) NOT NULL
, created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Users table
CREATE TABLE Users
( id int NOT NULL PRIMARY KEY AUTO_INCREMENT
, name varchar(255) NOT NULL
, email varchar(255) NOT NULL
, password varchar(255) NOT NULL
, created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create stats table
CREATE TABLE stats
( id int NOT NULL PRIMARY KEY AUTO_INCREMENT
, url_id int NOT NULL
, origin_id int NOT NULL
, created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create origin table
CREATE TABLE origin
( id int NOT NULL PRIMARY KEY AUTO_INCREMENT
, url varchar(255) NOT NULL
, created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed URLS table with dummy data
INSERT INTO urls values (1, 'http://www.google.com', 'test', curdate());