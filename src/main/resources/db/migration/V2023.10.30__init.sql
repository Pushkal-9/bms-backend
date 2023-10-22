CREATE TABLE `movie` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `rating` varchar(255) NOT NULL,
                          `language` varchar(255) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `release_date` date NOT NULL,
                          `description` varchar(255) NOT NULL,
                          `duration` varchar(255) NOT NULL,
                          `country` varchar(255) NOT NULL,
                          `genre` varchar(255),
                          `created_at` datetime DEFAULT NULL,
                          `updated_at` datetime DEFAULT NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE `city` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        `created_at` datetime DEFAULT NULL,
                        `updated_at` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
);


CREATE TABLE `theatre` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `address` varchar(255) NOT NULL,
                            `city_id` bigint(20) NOT NULL,
                            `name` varchar(255) NOT NULL,
                            `created_at` datetime DEFAULT NULL,
                            `updated_at` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (`city_id`) REFERENCES city(`id`)
);

CREATE TABLE `screen` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `theatre_id` bigint(20),
                          `name` varchar(255),
                          `created_at` datetime DEFAULT NULL,
                          `updated_at` datetime DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (`theatre_id`) REFERENCES theatre(`id`)
);

CREATE TABLE `shows` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `date` date NOT NULL,
                        `start_time` datetime NOT NULL,
                        `screen_id` bigint(20) DEFAULT NULL,
                        `movie_id` bigint(20) DEFAULT NULL,
                        `theatre_id` bigint(20) DEFAULT NULL,
                        `created_at` datetime DEFAULT NULL,
                        `updated_at` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`theatre_id`) REFERENCES `theatre` (`id`),
                        FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
                        FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`)
);

CREATE TABLE `seat` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `rate` int(11) NOT NULL,
                                 `row_number` varchar(255) NOT NULL,
                                 `column_number` varchar(255) NOT NULL,
                                 `show_id` bigint(20) NOT NULL,
                                 `available` BOOL DEFAULT 1,
                                 `booked_at` datetime DEFAULT NULL,
                                 `created_at` datetime DEFAULT NULL,
                                 `updated_at` datetime DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 FOREIGN KEY (`show_id`) REFERENCES `shows` (`id`)
);

-- For users
-- Create the users_table
CREATE TABLE users_table (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             login VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL
);

-- Create the password_reset_tokens table
CREATE TABLE password_reset_tokens (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       email VARCHAR(255) NOT NULL,
                                       token VARCHAR(255) NOT NULL,
                                       expiry_date TIMESTAMP NOT NULL
);
