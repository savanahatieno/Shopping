SET MODE PostgreSQL;

CREATE DATABASE shopping;
CREATE TABLE IF NOT EXISTS stores
(
    id  SERIAL PRIMARY KEY,
    name    VARCHAR,
    address VARCHAR,
    website VARCHAR
);

CREATE TABLE IF NOT EXISTS items
(
    id  SERIAL PRIMARY KEY,
    name    VARCHAR,
    price   INTEGER,
    storeid INTEGER
);

CREATE TABLE IF NOT EXISTS stores_items
(
    id SERIAL PRIMARY KEY,
    itemid INTEGER,
    storeid INTEGER
)


