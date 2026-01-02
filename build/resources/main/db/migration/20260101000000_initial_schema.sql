-- Filename: 20260101000000_initial_schema.sql

CREATE TABLE event (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    total_capacity INTEGER,
    price DOUBLE PRECISION,
    price_currency VARCHAR(5),
    seat_type INTEGER,
    buytime_start TIMESTAMP,
    buytime_end TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    INDEX `idx_event_name_buytime` (`name`, `buytime_start`, `buytime_end`)
);

CREATE TABLE "user" (
    id INT PRIMARY KEY AUTO_INCREMENT,
    phone_number VARCHAR(15),
    created_at TIMESTAMP
);

CREATE TABLE event_queue (
    id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    queue_number INTEGER,
    queue_key VARCHAR(15),
    status INTEGER,
    buy_expired_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    INDEX `idx_queue_status_event_user` (`event_id`, `user_id`, `status`)
);

CREATE TABLE event_transaction (
    id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    status INTEGER,
    price DOUBLE PRECISION,
    price_currency VARCHAR(5),
    payment_transaction_id VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    expired_at TIMESTAMP,
    paid_at TIMESTAMP,
    INDEX `idx_trx_event_user_status` (`event_id`, `user_id`, `status`),
    INDEX `idx_trx_user_trx_id` (`user_id`, `id`),
    INDEX `idx_trx_payment_transaction_id` (`payment_transaction_id`),
);

CREATE TABLE ticket (
    id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INTEGER NOT NULL,
    ticket_key VARCHAR(15),
    seat_id VARCHAR(10),
    status INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    INDEX `idx_ticket_status_event_seat_id` (`status`, `event_id`, `seat_id`)
);

CREATE TABLE user_ticket (
    id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    ticket_id INTEGER NOT NULL,
    status INTEGER,
    booked_at TIMESTAMP,
    booking_expired_at TIMESTAMP,
    issued_at TIMESTAMP,
    redeemed_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    INDEX `idx_user_ticket_trx_id` (`transaction_id`)
);