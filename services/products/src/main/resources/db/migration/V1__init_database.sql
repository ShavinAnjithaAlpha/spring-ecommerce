CREATE TABLE IF NOT EXISTS Category
(
    id INTEGER NOT NULL PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Product
(
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price NUMERIC(38, 2) NOT NULL,
    category_id INTEGER
            CONSTRAINT foriegnKeyCategoryConstraint REFERENCES Category
);

CREATE SEQUENCE IF NOT EXISTS category_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_seq INCREMENT BY 50;