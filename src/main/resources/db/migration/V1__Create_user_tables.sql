-- V1__Create_user_tables.sql

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    phone_number VARCHAR(20),
    profile_image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_is_active (is_active)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default admin user (password: Admin@123)
INSERT INTO users (email, password, first_name, last_name, role, is_active, is_email_verified)
VALUES ('admin@quizapp.com', '$2a$10$xXhZ8q7rXNVJMr5xKZLkzeEYYL5s0l.3QJYuQYdKhJYcVQTK.KJKO', 'Admin', 'User', 'ADMIN', TRUE, TRUE);

-- Insert sample teacher (password: Teacher@123)
INSERT INTO users (email, password, first_name, last_name, role, is_active, is_email_verified)
VALUES ('teacher@quizapp.com', '$2a$10$xXhZ8q7rXNVJMr5xKZLkzeEYYL5s0l.3QJYuQYdKhJYcVQTK.KJKO', 'John', 'Doe', 'TEACHER', TRUE, TRUE);

-- Insert sample student (password: Student@123)
INSERT INTO users (email, password, first_name, last_name, role, is_active, is_email_verified)
VALUES ('student@quizapp.com', '$2a$10$xXhZ8q7rXNVJMr5xKZLkzeEYYL5s0l.3QJYuQYdKhJYcVQTK.KJKO', 'Jane', 'Smith', 'STUDENT', TRUE, TRUE);