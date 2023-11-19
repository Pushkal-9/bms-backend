CREATE TABLE `booking` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `amount` int(11) NOT NULL,
                        `status` varchar(255) DEFAULT 'PENDING',
                        `booked_at` datetime DEFAULT NULL,
                        `customer_id` bigint(20) NOT NULL,
                        `email` varchar(255) NOT NULL,
                        `created_at` datetime DEFAULT NULL,
                        `updated_at` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

ALTER TABLE `seat` ADD blocked BOOL DEFAULT 0;
ALTER TABLE `seat` ADD blockedAt datetime DEFAULT NULL;