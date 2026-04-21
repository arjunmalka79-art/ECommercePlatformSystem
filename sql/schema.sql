-- ECommercePlatform Database Schema
-- Run this in Oracle SQL Developer

-- Create PRODUCTS table
CREATE TABLE PRODUCTS (
    productId NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    price NUMBER(10,2) NOT NULL,
    stock NUMBER NOT NULL
);

-- Create ORDERS table
CREATE TABLE ORDERS (
    orderId NUMBER PRIMARY KEY,
    customerId NUMBER NOT NULL,
    productName VARCHAR2(100) NOT NULL
);

-- Create sequences for auto-increment
CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE order_seq START WITH 1 INCREMENT BY 1;

-- Insert some sample products
INSERT INTO PRODUCTS VALUES (product_seq.NEXTVAL, 'Laptop', 55000.00, 10);
INSERT INTO PRODUCTS VALUES (product_seq.NEXTVAL, 'Mouse', 500.00, 50);
INSERT INTO PRODUCTS VALUES (product_seq.NEXTVAL, 'Keyboard', 1200.00, 30);
INSERT INTO PRODUCTS VALUES (product_seq.NEXTVAL, 'Headphones', 2500.00, 20);
INSERT INTO PRODUCTS VALUES (product_seq.NEXTVAL, 'USB Cable', 150.00, 100);

COMMIT;
