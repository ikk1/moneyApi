CREATE TABLE category  (
    code BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50)
);

INSERT INTO category(name) VALUES('Leisure');
INSERT INTO category(name) VALUES('Food');
INSERT INTO category(name) VALUES('Supermarket');
INSERT INTO category(name) VALUES('Transportation');
INSERT INTO category(name) VALUES('Health');
INSERT INTO category(name) VALUES('Others');