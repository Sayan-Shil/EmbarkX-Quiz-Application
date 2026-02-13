package com.embarkx.embarkxquiz.controller.student;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizDetailResponseDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizResponseDTO;
import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import com.embarkx.embarkxquiz.services.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/quizzes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
public class StudentQuizController {
    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> getPublishedQuizzes() {
        List<QuizResponseDTO> quizzes = quizService.getPublishedQuizzes();
        return ResponseEntity.ok(ApiResponse.success(quizzes, MessageConstants.QUIZZES_FETCHED_SUCCESS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResponseDTO>> getQuizById(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(ApiResponse.success(quiz, MessageConstants.QUIZ_FETCHED_SUCCESS));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<QuizDetailResponseDTO>> getQuizWithQuestions(@PathVariable Long id) {
        QuizDetailResponseDTO quiz = quizService.getQuizWithQuestions(id);
        return ResponseEntity.ok(ApiResponse.success(quiz, MessageConstants.QUIZ_FETCHED_SUCCESS));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> getQuizzesByDifficulty(@PathVariable QuizDifficulty difficulty) {
        List<QuizResponseDTO> quizzes = quizService.getQuizzesByDifficulty(difficulty);
        return ResponseEntity.ok(ApiResponse.success(quizzes, MessageConstants.QUIZZES_FETCHED_SUCCESS));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> getQuizzesByCategory(@PathVariable String category) {
        List<QuizResponseDTO> quizzes = quizService.getQuizzesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(quizzes, MessageConstants.QUIZZES_FETCHED_SUCCESS));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> searchQuizzes(@RequestParam String keyword) {
        List<QuizResponseDTO> quizzes = quizService.searchQuizzes(keyword);
        return ResponseEntity.ok(ApiResponse.success(quizzes, MessageConstants.QUIZZES_FETCHED_SUCCESS));
    }
}
