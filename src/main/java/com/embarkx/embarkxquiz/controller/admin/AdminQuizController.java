package com.embarkx.embarkxquiz.controller.admin;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.quiz.UpdateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizDetailResponseDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizResponseDTO;
import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import com.embarkx.embarkxquiz.services.quiz.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/quizzes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminQuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> getAllQuizzes() {
        List<QuizResponseDTO> quizzes = quizService.getAllQuizzes();
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResponseDTO>> updateQuiz(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuizRequestDTO request) {
        QuizResponseDTO quiz = quizService.updateQuiz(id, request);
        return ResponseEntity.ok(ApiResponse.success(quiz, MessageConstants.QUIZ_UPDATED_SUCCESS));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(null, MessageConstants.QUIZ_DELETED_SUCCESS));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<QuizResponseDTO>> publishQuiz(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.publishQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(quiz, "Quiz published successfully"));
    }

    @PatchMapping("/{id}/unpublish")
    public ResponseEntity<ApiResponse<QuizResponseDTO>> unpublishQuiz(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.unpublishQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(quiz, "Quiz unpublished successfully"));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<QuizResponseDTO>> activateQuiz(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.activateQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(quiz, "Quiz activated successfully"));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<QuizResponseDTO>> deactivateQuiz(@PathVariable Long id) {
        QuizResponseDTO quiz = quizService.deactivateQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(quiz, "Quiz deactivated successfully"));
    }

}
