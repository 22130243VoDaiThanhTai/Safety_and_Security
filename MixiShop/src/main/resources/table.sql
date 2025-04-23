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
-- Bảng lưu thông tin đơn hàng
CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        email VARCHAR(255),
                        phone VARCHAR(15),
                        address VARCHAR(255),
                        total_price DOUBLE NOT NULL,
                        status VARCHAR(20) DEFAULT 'PENDING',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES account(id)
);

-- Bảng chi tiết đơn hàng
CREATE TABLE order_details (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               order_id INT NOT NULL,
                               product_id INT NOT NULL,
                               quantity INT NOT NULL,
                               price DOUBLE NOT NULL,
                               FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                               FOREIGN KEY (product_id) REFERENCES product(id)
);
