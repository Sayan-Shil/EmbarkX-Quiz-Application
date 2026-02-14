package com.embarkx.embarkxquiz.controller.admin;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final ResultRepository resultRepository;
    private final SessionMapper sessionMapper;

    @GetMapping("/sessions/all")
    public ResponseEntity<ApiResponse<List<SessionResponseDTO>>> getAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionResponseDTO> sessionDTOs = sessions.stream()
                .map(sessionMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(sessionDTOs, "All sessions fetched successfully"));
    }

    @GetMapping("/results/all")
    public ResponseEntity<ApiResponse<List<ResultResponseDTO>>> getAllResults() {
        List<Result> results = resultRepository.findAll();
        List<ResultResponseDTO> resultDTOs = results.stream()
                .map(sessionMapper::toResultResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(resultDTOs, "All results fetched successfully"));
    }

    @GetMapping("/student/{studentId}/sessions")
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

    @GetMapping("/analytics/overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSystemOverview() {
        Map<String, Object> analytics = new HashMap<>();

        List<Session> allSessions = sessionRepository.findAll();
        List<Result> allResults = resultRepository.findAll();

        analytics.put("totalSessions", allSessions.size());
        analytics.put("completedSessions", allSessions.stream()
                .filter(s -> s.getStatus().name().equals("COMPLETED"))
                .count());
        analytics.put("totalResults", allResults.size());
        analytics.put("averageScore", allResults.stream()
                .mapToDouble(Result::getPercentage)
                .average()
                .orElse(0.0));
        analytics.put("passRate", allResults.stream()
                .filter(Result::getIsPassed)
                .count() * 100.0 / allResults.size());

        return ResponseEntity.ok(ApiResponse.success(analytics, "Analytics fetched successfully"));
    }

    @GetMapping("/quiz/{quizId}/analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getQuizAnalytics(@PathVariable Long quizId) {
        Map<String, Object> analytics = new HashMap<>();

        List<Session> quizSessions = sessionRepository.findByQuizId(quizId);
        List<Result> quizResults = resultRepository.findByQuizId(quizId);

        analytics.put("totalAttempts", quizSessions.size());
        analytics.put("completedAttempts", quizResults.size());
        analytics.put("averageScore", quizResults.stream()
                .mapToDouble(Result::getPercentage)
                .average()
                .orElse(0.0));
        analytics.put("passRate", quizResults.isEmpty() ? 0.0 :
                quizResults.stream().filter(Result::getIsPassed).count() * 100.0 / quizResults.size());
        analytics.put("averageTimeTaken", quizResults.stream()
                .mapToInt(Result::getTimeTakenMinutes)
                .average()
                .orElse(0.0));

        return ResponseEntity.ok(ApiResponse.success(analytics, "Quiz analytics fetched successfully"));
    }
}
