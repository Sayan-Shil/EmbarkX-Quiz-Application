package com.embarkx.embarkxquiz.dto.request.question;

import com.embarkx.embarkxquiz.enums.QuestionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuestionRequestDTO {

    @Size(min = 10, max = 1000, message = "Question text must be between 10 and 1000 characters")
    private String questionText;

    private QuestionType questionType;

    @Min(value = 1, message = "Marks must be at least 1")
    private Integer marks;

    private Integer orderIndex;

    @Size(max = 2000, message = "Explanation cannot exceed 2000 characters")
    private String explanation;

    private String imageUrl;

    private Boolean isRequired;
}
