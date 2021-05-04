SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS stores (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    address VARCHAR,
    website VARCHAR
);

CREATE TABLE IF NOT EXISTS items (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    price INTEGER,
    storeid INTEGER
)


