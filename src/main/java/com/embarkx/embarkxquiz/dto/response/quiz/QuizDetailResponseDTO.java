package com.embarkx.embarkxquiz.dto.response.quiz;

import com.embarkx.embarkxquiz.dto.response.question.QuestionResponseDTO;
import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class QuizDetailResponseDTO {

        private Long id;
        private String title;
        private String description;
        private QuizDifficulty difficulty;
        private Integer durationMinutes;
        private Integer passingScore;
        private Integer totalMarks;
        private Boolean isActive;
        private Boolean isPublished;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String category;
        private String instructions;
        private Integer maxAttempts;
        private Boolean shuffleQuestions;
        private Boolean showResultsImmediately;
        private Long createdByUserId;
        private String createdByUserName;
        private List<QuestionResponseDTO> questions;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}

