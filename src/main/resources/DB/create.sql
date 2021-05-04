SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS stores (
    id int PRIMARY KEY auto_incriment,
    name VARCHAR,
    address VARCHAR,
    website VARCHAR
);

CREATE TABLE IF NOT EXISTS items (
    id int PRIMARY KEY auto_incriment,
    name VARCHAR,
    storeid NUMBER
)


