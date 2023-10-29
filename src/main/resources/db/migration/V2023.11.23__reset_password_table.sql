CREATE TABLE password_reset_tokens (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       email VARCHAR(255) NOT NULL,
                                       token VARCHAR(255) NOT NULL,
                                       expiry_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);