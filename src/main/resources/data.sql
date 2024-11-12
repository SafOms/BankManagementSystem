-- Insert Users
INSERT INTO user (full_name, email, address) VALUES 
('John Doe', 'john@example.com', '123 Main St'), 
('Jane Smith', 'jane@example.com', '456 Elm St');

-- Insert Accounts
INSERT INTO account (balance, user_id, account_type, creation_date, interest_rate) VALUES 
(1200.00, 1, 'Personal', '2024-01-10', 0.0),
(1500.00, 1, 'Savings', '2024-02-15', 0.02),
(800.00, 2, 'Business', '2024-03-20', 0.0);

-- Insert Employees
INSERT INTO employee (name, email, position, hr_pin) VALUES
('Alice Johnson', 'alice.johnson@example.com', 'Manager', '1234'),
('Bob Williams', 'bob.williams@example.com', 'Teller', '1234');
