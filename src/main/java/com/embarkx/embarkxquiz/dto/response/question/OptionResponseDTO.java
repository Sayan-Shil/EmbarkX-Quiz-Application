package com.embarkx.embarkxquiz.dto.response.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionResponseDTO {
    private Long id;
    private String optionText;
    private Boolean isCorrect;
    private Integer orderIndex;
}
