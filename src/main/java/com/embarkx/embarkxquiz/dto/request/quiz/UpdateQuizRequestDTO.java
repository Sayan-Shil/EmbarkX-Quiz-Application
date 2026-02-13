package com.embarkx.embarkxquiz.dto.request.quiz;

import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuizRequestDTO {
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private QuizDifficulty difficulty;

    @Min(value = 5, message = "Duration must be at least 5 minutes")
    @Max(value = 180, message = "Duration cannot exceed 180 minutes")
    private Integer durationMinutes;

    @Min(value = 0, message = "Passing score must be at least 0")
    @Max(value = 100, message = "Passing score cannot exceed 100")
    private Integer passingScore;

    @Min(value = 1, message = "Total marks must be at least 1")
    private Integer totalMarks;

    private String category;

    @Size(max = 2000, message = "Instructions cannot exceed 2000 characters")
    private String instructions;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer maxAttempts;

    private Boolean shuffleQuestions;

    private Boolean showResultsImmediately;

    private Boolean isActive;

    private Boolean isPublished;
}
