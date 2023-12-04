CREATE TABLE user_payment_method (
                              id bigint(20) NOT NULL AUTO_INCREMENT,
                              user_id bigint(20) NOT NULL,
                              payment_method VARCHAR(50) NOT NULL,
                              card_number VARCHAR(20) NOT NULL,
                              expiration_date VARCHAR(20) NOT NULL,
                              cvv INT NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (user_id) REFERENCES users(id)
);