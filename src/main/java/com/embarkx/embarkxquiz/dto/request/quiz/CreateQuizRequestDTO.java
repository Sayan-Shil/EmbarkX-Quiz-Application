package com.embarkx.embarkxquiz.dto.request.quiz;

import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Difficulty is required")
    private QuizDifficulty difficulty;

    @NotNull(message = "Duration is required")
    @Min(value = 5, message = "Duration must be at least 5 minutes")
    @Max(value = 180, message = "Duration cannot exceed 180 minutes")
    private Integer durationMinutes;

    @NotNull(message = "Passing score is required")
    @Min(value = 0, message = "Passing score must be at least 0")
    @Max(value = 100, message = "Passing score cannot exceed 100")
    private Integer passingScore;

    @NotNull(message = "Total marks is required")
    @Min(value = 1, message = "Total marks must be at least 1")
    private Integer totalMarks;

    private String category;

    @Size(max = 2000, message = "Instructions cannot exceed 2000 characters")
    private String instructions;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer maxAttempts = 1;

    private Boolean shuffleQuestions = false;

    private Boolean showResultsImmediately = true;
}
