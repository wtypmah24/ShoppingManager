-- Создание таблицы customer
CREATE TABLE IF NOT EXISTS customer (
                                        id SERIAL PRIMARY KEY,
                                        first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
    );

-- Создание таблицы customer_address
CREATE TABLE IF NOT EXISTS customer_address (
                                                customer_id BIGINT NOT NULL,
                                                street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    house_number INT NOT NULL,
    zip_code INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
    );

-- Создание таблицы product
CREATE TABLE IF NOT EXISTS product (
                                       product_code BIGINT PRIMARY KEY,
                                       product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    );

-- Создание таблицы basket
CREATE TABLE IF NOT EXISTS basket (
                                      id SERIAL PRIMARY KEY,
                                      customer_id BIGINT NOT NULL,
                                      FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
    );

-- Вставка данных в таблицу customer
INSERT INTO customer (first_name, last_name, email)
VALUES ('John', 'Wick', 'killer@example.com'),
       ('Daenerys ', 'Targaryen', 'dragon-mother@example.com'),
       ('John ', 'Snow', 'black-watch@example.com'),
       ('Ramsy ', 'Bolton', 'bastard@example.com');

-- Вставка данных в таблицу customer_address
INSERT INTO customer_address (customer_id, street, city, house_number, zip_code)
VALUES ((SELECT id FROM customer WHERE email = 'killer@example.com'), 'Main Street', 'Anytown', 123, 12345),
       ((SELECT id FROM customer WHERE email = 'dragon-mother@example.com'), 'Iron Throne', 'Westeros', 46, 67390),
       ((SELECT id FROM customer WHERE email = 'black-watch@example.com'), 'Black Castle', 'The Wall', 45, 67850),
       ((SELECT id FROM customer WHERE email = 'bastard@example.com'), 'Winterfall', 'The North', 456, 27890);

-- Вставка данных в таблицу product
INSERT INTO product (product_code, product_name, price)
VALUES (1001, 'Valyrian Sword', 10.99),
       (1002, 'Dragon Egg', 20.99),
       (1003, 'Wild Fire', 30.99);

-- Вставка данных в таблицу basket
INSERT INTO basket (customer_id)
VALUES ((SELECT id FROM customer WHERE email = 'killer@example.com')),
       ((SELECT id FROM customer WHERE email = 'dragon-mother@example.com'));
