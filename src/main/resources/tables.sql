-- CREATE DATABASE library

CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(1) NOT NULL
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    rent_price DECIMAL DEFAULT 0,
    rented VARCHAR(1) NOT NULL DEFAULT 'N'
);

CREATE TABLE rent_transactions (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    rental_date DATE NOT NULL DEFAULT CURRENT_DATE,
    return_date DATE NOT NULL,
    TOTAL DECIMAL DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE rent_transaction_details (
    id SERIAL PRIMARY KEY,
    transaction_id INT NOT NULL,
    book_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES rent_transactions(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON UPDATE CASCADE ON DELETE CASCADE
);
