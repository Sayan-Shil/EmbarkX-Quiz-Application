package com.embarkx.embarkxquiz.mapper;

import com.embarkx.embarkxquiz.dto.response.quiz.QuizDetailResponseDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizResponseDTO;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {
    public QuizResponseDTO toResponseDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        return QuizResponseDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .difficulty(quiz.getDifficulty())
                .durationMinutes(quiz.getDurationMinutes())
                .passingScore(quiz.getPassingScore())
                .totalMarks(quiz.getTotalMarks())
                .isActive(quiz.getIsActive())
                .isPublished(quiz.getIsPublished())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .category(quiz.getCategory())
                .instructions(quiz.getInstructions())
                .maxAttempts(quiz.getMaxAttempts())
                .shuffleQuestions(quiz.getShuffleQuestions())
                .showResultsImmediately(quiz.getShowResultsImmediately())
                .createdByUserId(quiz.getCreatedByUser() != null ? quiz.getCreatedByUser().getId() : null)
                .createdByUserName(quiz.getCreatedByUser() != null ?
                        quiz.getCreatedByUser().getFirstName() + " " + quiz.getCreatedByUser().getLastName() : null)
                .totalQuestions(quiz.getQuestions() != null ? quiz.getQuestions().size() : 0)
//                .createdAt(quiz.getCreatedAt())
//                .updatedAt(quiz.getUpdatedAt())
                .build();
    }

    public QuizDetailResponseDTO toDetailResponseDTO(Quiz quiz, QuestionMapper questionMapper) {
        if (quiz == null) {
            return null;
        }

        return QuizDetailResponseDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .difficulty(quiz.getDifficulty())
                .durationMinutes(quiz.getDurationMinutes())
                .passingScore(quiz.getPassingScore())
                .totalMarks(quiz.getTotalMarks())
                .isActive(quiz.getIsActive())
                .isPublished(quiz.getIsPublished())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .category(quiz.getCategory())
                .instructions(quiz.getInstructions())
                .maxAttempts(quiz.getMaxAttempts())
                .shuffleQuestions(quiz.getShuffleQuestions())
                .showResultsImmediately(quiz.getShowResultsImmediately())
                .createdByUserId(quiz.getCreatedByUser() != null ? quiz.getCreatedByUser().getId() : null)
                .createdByUserName(quiz.getCreatedByUser() != null ?
                        quiz.getCreatedByUser().getFirstName() + " " + quiz.getCreatedByUser().getLastName() : null)
                .questions(quiz.getQuestions() != null ?
                        quiz.getQuestions().stream().map(questionMapper::toResponseDTO).toList() : null)
//                .createdAt(quiz.getCreatedAt())
//                .updatedAt(quiz.getUpdatedAt())
                .build();
    }
}
