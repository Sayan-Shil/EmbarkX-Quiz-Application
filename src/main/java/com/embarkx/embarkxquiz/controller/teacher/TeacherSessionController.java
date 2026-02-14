package com.embarkx.embarkxquiz.controller.teacher;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.session.ResultResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.SessionResponseDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.mapper.SessionMapper;
import com.embarkx.embarkxquiz.models.session.Result;
import com.embarkx.embarkxquiz.models.session.Session;
import com.embarkx.embarkxquiz.repositories.session.ResultRepository;
import com.embarkx.embarkxquiz.repositories.session.SessionRepository;
import com.embarkx.embarkxquiz.services.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teacher/sessions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
public class TeacherSessionController {
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final ResultRepository resultRepository;
    private final SessionMapper sessionMapper;

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<ApiResponse<List<SessionResponseDTO>>> getSessionsByQuiz(@PathVariable Long quizId) {
        List<SessionResponseDTO> sessions = sessionService.getSessionsByQuiz(quizId);
        return ResponseEntity.ok(ApiResponse.success(sessions, "Sessions fetched successfully"));
    }

    @GetMapping("/quiz/{quizId}/results")
    public ResponseEntity<ApiResponse<List<ResultResponseDTO>>> getResultsByQuiz(@PathVariable Long quizId) {
        List<Result> results = resultRepository.findByQuizId(quizId);
        List<ResultResponseDTO> resultDTOs = results.stream()
                .map(sessionMapper::toResultResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(resultDTOs, MessageConstants.RESULTS_FETCHED_SUCCESS));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<SessionResponseDTO>>> getSessionsByStudent(@PathVariable Long studentId) {
        List<SessionResponseDTO> sessions = sessionService.getSessionsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(sessions, "Sessions fetched successfully"));
    }

    @GetMapping("/student/{studentId}/results")
    public ResponseEntity<ApiResponse<List<ResultResponseDTO>>> getResultsByStudent(@PathVariable Long studentId) {
        List<Result> results = resultRepository.findByStudentId(studentId);
        List<ResultResponseDTO> resultDTOs = results.stream()
                .map(sessionMapper::toResultResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(resultDTOs, MessageConstants.RESULTS_FETCHED_SUCCESS));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<SessionResponseDTO>> getSessionById(@PathVariable Long sessionId) {
        SessionResponseDTO session = sessionService.getSessionById(sessionId);
        return ResponseEntity.ok(ApiResponse.success(session, "Session fetched successfully"));
    }

    @GetMapping("/{sessionId}/result")
    public ResponseEntity<ApiResponse<ResultResponseDTO>> getResultBySession(@PathVariable Long sessionId) {
        Result result = resultRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Result not found for this session"));

        return ResponseEntity.ok(ApiResponse.success(
                sessionMapper.toResultResponseDTO(result),
                MessageConstants.RESULT_FETCHED_SUCCESS));
    }
}
