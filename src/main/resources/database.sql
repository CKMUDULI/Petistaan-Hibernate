DROP DATABASE IF EXISTS petistaan;
CREATE DATABASE petistaan;
USE petistaan;

DROP TABLE IF EXISTS owner_pet_table;
DROP TABLE IF EXISTS domestic_pet_table;
DROP TABLE IF EXISTS wild_pet_table;
DROP TABLE IF EXISTS pet_table;
DROP TABLE IF EXISTS owner_table;

CREATE TABLE owner_table (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL UNIQUE,
    email_id VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE pet_table (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE TABLE domestic_pet_table (
    id INT PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    FOREIGN KEY (id) REFERENCES pet_table(id)
);

CREATE TABLE wild_pet_table (
    id INT PRIMARY KEY,
    place_of_birth VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES pet_table(id)
);

CREATE TABLE owner_pet_table (
    owner_id INT NOT NULL,
    pet_id INT NOT NULL,
    PRIMARY KEY (owner_id, pet_id),
    FOREIGN KEY (owner_id) REFERENCES owner_table(id),
    FOREIGN KEY (pet_id) REFERENCES pet_table(id)
);

-- Create indexes for better performance
CREATE INDEX idx_owner_email ON owner_table(email_id);
CREATE INDEX idx_owner_mobile ON owner_table(mobile_number);
CREATE INDEX idx_owner_name ON owner_table(first_name, last_name);
CREATE INDEX idx_pet_name ON pet_table(name);
CREATE INDEX idx_pet_type ON pet_table(type);