-- ${flyway:timestamp}

DROP TABLE IF EXISTS orderItems;
DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;

DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE users
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    emailAddress VARCHAR(50) NOT NULL UNIQUE,
    phoneNumber VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roleId INTEGER NOT NULL,

    FOREIGN KEY (roleId) REFERENCES roles(id)
);

CREATE TABLE addresses
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    addressLine TEXT NOT NULL,
    userId INTEGER NOT NULL,

    FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE categories
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE products
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    quantity INTEGER NOT NULL,
    description TEXT NOT NULL,
    amount FLOAT NOT NULL,
    color VARCHAR(20) NOT NULL,
    categoryId INTEGER NOT NULL,

    FOREIGN KEY (categoryId) REFERENCES categories(id)
);

CREATE TABLE orders
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    amount FLOAT NOT NULL,
    status VARCHAR(20) NOT NULL,
    driverId INTEGER,

    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (driverId) REFERENCES users(id)
);

CREATE TABLE orderItems
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    orderId INTEGER NOT NULL,
    productId INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    amount FLOAT NOT NULL,
    total FLOAT NOT NULL,

    FOREIGN KEY (orderId) REFERENCES orders(id),
    FOREIGN KEY (productId) REFERENCES products(id)
);

INSERT INTO roles(name)
VALUES ('admin'),
       ('driver'),
       ('customer');

INSERT INTO categories(name)
VALUES ('shirt'),
       ('polo'),
       ('dress'),
       ('short'),
       ('trousers');