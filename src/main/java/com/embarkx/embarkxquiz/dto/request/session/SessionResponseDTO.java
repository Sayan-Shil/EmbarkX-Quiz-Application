package com.embarkx.embarkxquiz.dto.request.session;

import com.embarkx.embarkxquiz.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponseDTO {
    private Long id;
    private Long quizId;
    private String quizTitle;
    private Long studentId;
    private String studentName;
    private SessionStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime submitTime;
    private Integer timeTakenMinutes;
    private Integer scoreObtained;
    private Integer totalScore;
    private Double percentage;
    private Boolean isPassed;
    private Integer attemptNumber;
    private Integer durationMinutes;
    private LocalDateTime createdAt;
}
