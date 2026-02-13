package com.embarkx.embarkxquiz.controller.teacher;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.question.CreateQuestionRequestDTO;
import com.embarkx.embarkxquiz.dto.request.question.UpdateQuestionRequestDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.dto.response.question.QuestionResponseDTO;
import com.embarkx.embarkxquiz.services.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/questions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
public class TeacherQuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(
            @Valid @RequestBody CreateQuestionRequestDTO request) {

        QuestionResponseDTO question = questionService.createQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(question, MessageConstants.QUESTION_CREATED_SUCCESS));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getQuestionsByQuizId(@PathVariable Long quizId) {
        List<QuestionResponseDTO> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(ApiResponse.success(questions, "Questions fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionById(@PathVariable Long id) {
        QuestionResponseDTO question = questionService.getQuestionById(id);
        return ResponseEntity.ok(ApiResponse.success(question, "Question fetched successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuestionRequestDTO request) {

        QuestionResponseDTO question = questionService.updateQuestion(id, request);
        return ResponseEntity.ok(ApiResponse.success(question, MessageConstants.QUESTION_UPDATED_SUCCESS));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(ApiResponse.success(null, MessageConstants.QUESTION_DELETED_SUCCESS));
    }

    @PutMapping("/quiz/{quizId}/reorder")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> reorderQuestions(
            @PathVariable Long quizId,
            @RequestBody List<Long> questionIds) {

        List<QuestionResponseDTO> questions = questionService.reorderQuestions(quizId, questionIds);
        return ResponseEntity.ok(ApiResponse.success(questions, "Questions reordered successfully"));
    }
}
