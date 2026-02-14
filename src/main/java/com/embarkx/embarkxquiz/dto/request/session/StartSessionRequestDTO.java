package com.embarkx.embarkxquiz.dto.request.session;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartSessionRequestDTO {
    @NotNull(message = "Quiz ID is required")
    private Long quizId;
}
