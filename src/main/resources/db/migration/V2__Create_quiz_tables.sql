-- V2__Create_quiz_tables.sql

CREATE TABLE IF NOT EXISTS quizzes (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    difficulty VARCHAR(50) NOT NULL,
    duration_minutes INT NOT NULL,
    passing_score INT NOT NULL,
    total_marks INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_published BOOLEAN DEFAULT FALSE,
    start_time TIMESTAMP NULL,
    end_time TIMESTAMP NULL,
    created_by_user_id BIGINT,
    category VARCHAR(100),
    instructions VARCHAR(2000),
    max_attempts INT DEFAULT 1,
    shuffle_questions BOOLEAN DEFAULT FALSE,
    show_results_immediately BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    FOREIGN KEY (created_by_user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_difficulty (difficulty),
    INDEX idx_is_published (is_published),
    INDEX idx_is_active (is_active),
    INDEX idx_category (category),
    INDEX idx_created_by_user_id (created_by_user_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS questions (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         quiz_id BIGINT NOT NULL,
                                         question_text VARCHAR(1000) NOT NULL,
    question_type VARCHAR(50) NOT NULL,
    marks INT NOT NULL,
    order_index INT,
    explanation VARCHAR(2000),
    image_url VARCHAR(500),
    is_required BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    INDEX idx_quiz_id (quiz_id),
    INDEX idx_question_type (question_type),
    INDEX idx_order_index (order_index)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS options (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       question_id BIGINT NOT NULL,
                                       option_text VARCHAR(500) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    order_index INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    INDEX idx_question_id (question_id),
    INDEX idx_is_correct (is_correct),
    INDEX idx_order_index (order_index)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;