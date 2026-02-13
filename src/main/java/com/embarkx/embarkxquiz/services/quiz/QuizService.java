package com.embarkx.embarkxquiz.services.quiz;

import com.embarkx.embarkxquiz.dto.request.quiz.CreateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.request.quiz.UpdateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizDetailResponseDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizResponseDTO;
import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    QuizResponseDTO createQuiz(CreateQuizRequestDTO request, Long userId);
    QuizResponseDTO updateQuiz(Long quizId, UpdateQuizRequestDTO request);
    void deleteQuiz(Long quizId);
    QuizResponseDTO getQuizById(Long quizId);
    QuizDetailResponseDTO getQuizWithQuestions(Long quizId);
    List<QuizResponseDTO> getAllQuizzes();
    List<QuizResponseDTO> getPublishedQuizzes();
    List<QuizResponseDTO> getQuizzesByDifficulty(QuizDifficulty difficulty);
    List<QuizResponseDTO> getQuizzesByCategory(String category);
    List<QuizResponseDTO> getQuizzesByCreator(Long userId);
    List<QuizResponseDTO> searchQuizzes(String keyword);
    QuizResponseDTO publishQuiz(Long quizId);
    QuizResponseDTO unpublishQuiz(Long quizId);
    QuizResponseDTO activateQuiz(Long quizId);
    QuizResponseDTO deactivateQuiz(Long quizId);
}
