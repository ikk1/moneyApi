CREATE TABLE person (
    code BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50),
    active BOOLEAN,
    address VARCHAR(100),
    address_line2 VARCHAR(100),
    address_number VARCHAR(10),
    district VARCHAR(50),
    zip_code VARCHAR(10)   
);

INSERT INTO person (name, active, address, address_line2, address_number, district, zip_code)
VALUES ('John Doe', TRUE, '123 Main Street', 'Apt 101', '1234', 'Downtown', '12345');

INSERT INTO person (name, active, address, address_line2, address_number, district, zip_code)
VALUES ('Jane Smith', TRUE, '456 Elm Street', NULL, '5678', 'Uptown', '54321');

INSERT INTO person (name, active, address, address_line2, address_number, district, zip_code)
VALUES ('Alice Johnson', FALSE, '789 Oak Avenue', NULL, '91011', 'Suburbia', '67890');

INSERT INTO person (name, active, address, address_line2, address_number, district, zip_code)
VALUES ('Bob Brown', TRUE, '321 Pine Road', 'Suite B', '1213', 'Rural', '13579');

INSERT INTO person (name, active, address, address_line2, address_number, district, zip_code)
VALUES ('Maria Garcia', TRUE, '876 Maple Lane', NULL, '1415', 'Countryside', '97531');
