DROP DATABASE IF EXISTS ecommerce;

CREATE DATABASE ecommerce;

\c ecommerce

SET client_encoding='UTF8';

CREATE SCHEMA ecommerce;

CREATE TABLE ecommerce.user (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(20) NOT NULL UNIQUE,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    email VARCHAR(64) NOT NULL UNIQUE,
    phone VARCHAR(15) UNIQUE,
    password VARCHAR(255) NOT NULL,
    registration_date DATE NOT NULL,
    last_connection TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE ecommerce.cart (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    amount NUMERIC(1000, 2) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES ecommerce.user (id),
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.role (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES ecommerce.user (id),
    FOREIGN KEY (role_id) REFERENCES ecommerce.role (id)
);

CREATE TABLE ecommerce.country (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(52) NOT NULL UNIQUE,
    numeric_code INT NOT NULL UNIQUE,
    alpha_2_code VARCHAR(2) NOT NULL UNIQUE,
    alpha_3_code VARCHAR(3) NOT NULL UNIQUE,
    VAT INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.address (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(139) NOT NULL,
    zipcode INT NOT NULL,
    is_default_shipping BOOLEAN NOT NULL,
    is_default_billing BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL,
    country_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (country_id) REFERENCES ecommerce.country (id),
    FOREIGN KEY (user_id) REFERENCES ecommerce.user (id)
);

CREATE TABLE ecommerce.credit_card_type (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.credit_card (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    number BIGINT NOT NULL,
    holder_name VARCHAR(50) NOT NULL,
    expiration_date DATE NOT NULL,
    CVC INT NOT NULL,
    is_default BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL,
    credit_card_type_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (credit_card_type_id) REFERENCES ecommerce.credit_card_type (id),
    FOREIGN KEY (user_id) REFERENCES ecommerce.user (id)
);

CREATE TABLE ecommerce.category (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL UNIQUE,
    image BYTEA NOT NULL,
    is_active BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.product (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR NOT NULL,
    dimensions VARCHAR(100),
    weight INT,
    country_origin VARCHAR(52) NOT NULL,
    manufacturer VARCHAR(50) NOT NULL,
    quantity_available INT NOT NULL,
    price NUMERIC(1000, 2) NOT NULL,
    rating INT,
    creation_date DATE NOT NULL,
    last_update DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    seller_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (seller_id) REFERENCES ecommerce.user (id)
);

CREATE TABLE ecommerce.product_category (
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES ecommerce.product (id),
    FOREIGN KEY (category_id) REFERENCES ecommerce.category (id)
);

CREATE TABLE ecommerce.image (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    file BYTEA NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES ecommerce.product (id)
);

CREATE TABLE ecommerce.delivery_option (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    price NUMERIC(1000, 2) NOT NULL,
    time INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.promotion (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    code VARCHAR(20) NOT NULL UNIQUE,
    percentage INT NOT NULL,
    expiration_date DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.order (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    amount NUMERIC(1000, 2) NOT NULL,
    order_date DATE NOT NULL,
    delivery_date DATE NOT NULL,
    delivery_option_id INT NOT NULL,
    shipping_address_id INT NOT NULL,
    billing_address_id INT NOT NULL,
    promotion_id INT,
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (delivery_option_id) REFERENCES ecommerce.delivery_option,
    FOREIGN KEY (billing_address_id) REFERENCES ecommerce.address (id),
    FOREIGN KEY (shipping_address_id) REFERENCES ecommerce.address (id),
    FOREIGN KEY (promotion_id) REFERENCES ecommerce.promotion (id),
    FOREIGN KEY (user_id) REFERENCES ecommerce.user (id)
);

CREATE TABLE ecommerce.suborder_status (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.suborder (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    order_id INT NOT NULL,
    suborder_status_id INT NOT NULL,
    seller_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES ecommerce.order (id),
    FOREIGN KEY (suborder_status_id) REFERENCES ecommerce.suborder_status (id),
    FOREIGN KEY (seller_id) REFERENCES ecommerce.user (id)
);

CREATE TABLE ecommerce.item (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    quantity INT NOT NULL,
    is_gift BOOLEAN NOT NULL,
    price NUMERIC(1000, 2) NOT NULL,
    product_id INT NOT NULL,
    cart_id INT,
    suborder_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES ecommerce.product (id),
    FOREIGN KEY (cart_id) REFERENCES ecommerce.cart (id),
    FOREIGN KEY (suborder_id) REFERENCES ecommerce.suborder (id)
);

CREATE TABLE ecommerce.payment_status (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ecommerce.payment (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    amount NUMERIC(1000, 2) NOT NULL,
    date DATE NOT NULL,
    payment_status_id INT NOT NULL,
    credit_card_id INT NOT NULL,
    order_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (payment_status_id) REFERENCES ecommerce.payment_status (id),
    FOREIGN KEY (credit_card_id) REFERENCES ecommerce.credit_card (id),
    FOREIGN KEY (order_id) REFERENCES ecommerce.order (id)
);

CREATE TABLE ecommerce.review (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    content VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    publication_date TIMESTAMP NOT NULL,
    last_update TIMESTAMP NOT NULL,
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO ecommerce.role (name) VALUES ('CUSTOMER');
INSERT INTO ecommerce.role (name) VALUES ('SELLER');
INSERT INTO ecommerce.role (name) VALUES ('ADMIN');

INSERT INTO ecommerce.country (name, numeric_code, alpha_2_code, alpha_3_code, VAT) VALUES ('France', 250, 'FR', 'FRA', 20);

INSERT INTO ecommerce.credit_card_type (name) VALUES ('VISA');
INSERT INTO ecommerce.credit_card_type (name) VALUES ('MASTERCARD');
INSERT INTO ecommerce.credit_card_type (name) VALUES ('AMERICAN_EXPRESS');

INSERT INTO ecommerce.delivery_option (name, price, time) VALUES ('STANDARD', 10, 7);
INSERT INTO ecommerce.delivery_option (name, price, time) VALUES ('EXPRESS', 15, 3);

INSERT INTO ecommerce.suborder_status (name) VALUES ('NEW');
INSERT INTO ecommerce.suborder_status (name) VALUES ('IN_PROGRESS');
INSERT INTO ecommerce.suborder_status (name) VALUES ('SHIPPED');
INSERT INTO ecommerce.suborder_status (name) VALUES ('DELIVERED');
INSERT INTO ecommerce.suborder_status (name) VALUES ('CANCELED');

INSERT INTO ecommerce.payment_status (name) VALUES ('PENDING');
INSERT INTO ecommerce.payment_status (name) VALUES ('RECEIVED');
INSERT INTO ecommerce.payment_status (name) VALUES ('FAILED');