package com.embarkx.embarkxquiz.mapper;

import com.embarkx.embarkxquiz.dto.response.question.OptionResponseDTO;
import com.embarkx.embarkxquiz.dto.response.question.QuestionResponseDTO;
import com.embarkx.embarkxquiz.models.option.Option;
import com.embarkx.embarkxquiz.models.question.Question;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    public QuestionResponseDTO toResponseDTO(Question question) {
        if (question == null) {
            return null;
        }

        return QuestionResponseDTO.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType())
                .marks(question.getMarks())
                .orderIndex(question.getOrderIndex())
                .explanation(question.getExplanation())
                .imageUrl(question.getImageUrl())
                .isRequired(question.getIsRequired())
                .options(question.getOptions() != null ?
                        question.getOptions().stream().map(this::toOptionResponseDTO).collect(Collectors.toList()) : null)
                .build();
    }

    public OptionResponseDTO toOptionResponseDTO(Option option) {
        if (option == null) {
            return null;
        }

        return OptionResponseDTO.builder()
                .id(option.getId())
                .optionText(option.getOptionText())
                .isCorrect(option.getIsCorrect())
                .orderIndex(option.getOrderIndex())
                .build();
    }
}
