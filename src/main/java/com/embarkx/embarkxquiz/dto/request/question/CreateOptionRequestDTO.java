package com.embarkx.embarkxquiz.dto.request.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOptionRequestDTO {
    @NotBlank(message = "Option text is required")
    @Size(min = 1, max = 500, message = "Option text must be between 1 and 500 characters")
    private String optionText;

    private Boolean isCorrect = false;

    private Integer orderIndex;
}
