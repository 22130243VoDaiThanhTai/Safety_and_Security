CREATE DATABASE mixishop;

USE mixishop;

CREATE TABLE category (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(25) NOT NULL
);

CREATE TABLE product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         image VARCHAR(255),
                         price INT NOT NULL,
                         quantity INT NOT NULL,
                         categoryID INT,
                         FOREIGN KEY (categoryID) REFERENCES category(id)
);

CREATE TABLE account (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255),
                         email VARCHAR(255),
                         address VARCHAR(255),
                         role INT,
                         phone VARCHAR(15)
);
