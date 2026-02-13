package com.embarkx.embarkxquiz.controller.teacher;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.quiz.CreateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.request.quiz.UpdateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizDetailResponseDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizResponseDTO;
import com.embarkx.embarkxquiz.services.quiz.QuizService;
import com.embarkx.embarkxquiz.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/quizzes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
public class TeacherQuizController {

    private final QuizService quizService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuizResponseDTO>> createQuiz(
            @Valid @RequestBody CreateQuizRequestDTO request,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO currentUser = userService.getUserByEmail(userDetails.getUsername());

        QuizResponseDTO quiz = quizService.createQuiz(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(quiz, MessageConstants.QUIZ_CREATED_SUCCESS));
    }

    @GetMapping("/my-quizzes")
    public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> getMyQuizzes(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO currentUser = userService.getUserByEmail(userDetails.getUsername());

        List<QuizResponseDTO> quizzes = quizService.getQuizzesByCreator(currentUser.getId());
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
}
