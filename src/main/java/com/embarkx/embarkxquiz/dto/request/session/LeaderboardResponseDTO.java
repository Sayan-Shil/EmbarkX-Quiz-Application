package com.embarkx.embarkxquiz.dto.request.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaderboardResponseDTO {
    private Integer rank;
    private Long studentId;
    private String studentName;
    private Integer scoreObtained;
    private Integer totalScore;
    private Double percentage;
    private Integer timeTakenMinutes;
}
