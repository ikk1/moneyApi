CREATE TABLE expense  (
    code BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    due_date DATE NOT NULL,
    payment_date DATE NOT NULL,
    value DECIMAL(10,2) NOT NULL,
    notes VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    category_code BIGINT NOT NULL,
    person_code BIGINT NOT NULL,
    FOREIGN KEY (category_code) REFERENCES category(code),
    FOREIGN KEY (person_code) REFERENCES person(code) 
);

INSERT INTO expense (description, due_date, payment_date, value, notes, type, category_code, person_code)
VALUES 
    ('Rent payment', '2024-03-01', '2024-03-01', -1000.00, 'Monthly rent for March', 'EXPENSE', 1, 1),
    ('Internet bill', '2024-03-05', '2024-03-05', -80.00, 'Monthly internet bill', 'EXPENSE', 2, 2),
    ('Salary deposit', '2024-03-10', '2024-03-10', 3000.00, 'Monthly salary', 'REVENUE', 3, 3),
    ('Grocery shopping', '2024-03-15', '2024-03-15', -200.00, 'Weekly groceries', 'EXPENSE', 4, 4),
    ('Electricity bill', '2024-03-20', '2024-03-20', -120.00, 'Monthly electricity bill', 'EXPENSE', 5, 5),
    ('Freelance income', '2024-03-25', '2024-03-25', 500.00, 'Payment for freelance work', 'REVENUE', 3, 1),
    ('Phone bill', '2024-03-28', '2024-03-28', -50.00, 'Monthly phone bill', 'EXPENSE', 2, 2),
    ('Dinner out', '2024-03-12', '2024-03-12', -75.00, 'Dinner with friends', 'EXPENSE', 4, 4),
    ('Investment dividend', '2024-03-18', '2024-03-18', 150.00, 'Dividend from investment', 'REVENUE', 3, 3),
    ('Gasoline purchase', '2024-03-22', '2024-03-22', -40.00, 'Refill gasoline for the car', 'EXPENSE', 5, 5);
