package com.embarkx.embarkxquiz.services.question;

import com.embarkx.embarkxquiz.dto.request.question.CreateQuestionRequestDTO;
import com.embarkx.embarkxquiz.dto.request.question.UpdateQuestionRequestDTO;
import com.embarkx.embarkxquiz.dto.response.question.QuestionResponseDTO;

import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(CreateQuestionRequestDTO request);

    QuestionResponseDTO updateQuestion(Long questionId, UpdateQuestionRequestDTO request);

    void deleteQuestion(Long questionId);

    QuestionResponseDTO getQuestionById(Long questionId);

    List<QuestionResponseDTO> getQuestionsByQuizId(Long quizId);

    List<QuestionResponseDTO> reorderQuestions(Long quizId, List<Long> questionIds);
}
