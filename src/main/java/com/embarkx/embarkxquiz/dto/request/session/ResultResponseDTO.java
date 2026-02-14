package com.embarkx.embarkxquiz.dto.request.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultResponseDTO {
    private Long id;
    private Long sessionId;
    private Long quizId;
    private String quizTitle;
    private Long studentId;
    private String studentName;
    private Integer scoreObtained;
    private Integer totalScore;
    private Double percentage;
    private Boolean isPassed;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private Integer unanswered;
    private Integer timeTakenMinutes;
    private Integer rank;
    private String feedback;
    private LocalDateTime createdAt;
}
