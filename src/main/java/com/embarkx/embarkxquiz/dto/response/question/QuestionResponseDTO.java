package com.embarkx.embarkxquiz.dto.response.question;

import com.embarkx.embarkxquiz.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private QuestionType questionType;
    private Integer marks;
    private Integer orderIndex;
    private String explanation;
    private String imageUrl;
    private Boolean isRequired;
    private List<OptionResponseDTO> options;
}
