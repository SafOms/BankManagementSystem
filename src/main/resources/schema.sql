-- Create User table
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Create Employee table
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    position VARCHAR(50),
    hr_pin VARCHAR(10) NOT NULL
);

-- Create Account table
CREATE TABLE IF NOT EXISTS account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DOUBLE NOT NULL,
    user_id BIGINT,
    account_type VARCHAR(20) NOT NULL,
    creation_date DATE NOT NULL,
    interest_rate DOUBLE DEFAULT 0.0,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
