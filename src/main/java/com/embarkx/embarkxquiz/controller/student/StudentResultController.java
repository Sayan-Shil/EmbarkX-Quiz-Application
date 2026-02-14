package com.embarkx.embarkxquiz.controller.student;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.session.LeaderboardResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.ResultResponseDTO;
import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.mapper.SessionMapper;
import com.embarkx.embarkxquiz.models.session.Result;
import com.embarkx.embarkxquiz.repositories.session.ResultRepository;
import com.embarkx.embarkxquiz.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/student/results")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
public class StudentResultController {

    private final ResultRepository resultRepository;
    private final UserService userService;
    private final SessionMapper sessionMapper;

    @GetMapping("/my-results")
    public ResponseEntity<ApiResponse<List<ResultResponseDTO>>> getMyResults(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO currentUser = userService.getUserByEmail(userDetails.getUsername());

        List<Result> results = resultRepository.findByStudentId(currentUser.getId());
        List<ResultResponseDTO> resultDTOs = results.stream()
                .map(sessionMapper::toResultResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(resultDTOs, MessageConstants.RESULTS_FETCHED_SUCCESS));
    }

    @GetMapping("/{resultId}")
    public ResponseEntity<ApiResponse<ResultResponseDTO>> getResultById(@PathVariable Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        return ResponseEntity.ok(ApiResponse.success(
                sessionMapper.toResultResponseDTO(result),
                MessageConstants.RESULT_FETCHED_SUCCESS));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ApiResponse<ResultResponseDTO>> getResultBySessionId(@PathVariable Long sessionId) {
        Result result = resultRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Result not found for this session"));

        return ResponseEntity.ok(ApiResponse.success(
                sessionMapper.toResultResponseDTO(result),
                MessageConstants.RESULT_FETCHED_SUCCESS));
    }

    @GetMapping("/quiz/{quizId}/leaderboard")
    public ResponseEntity<ApiResponse<List<LeaderboardResponseDTO>>> getLeaderboard(@PathVariable Long quizId) {
        List<Result> results = resultRepository.findLeaderboardByQuizId(quizId);

        List<LeaderboardResponseDTO> leaderboard = IntStream.range(0, results.size())
                .mapToObj(i -> sessionMapper.toLeaderboardDTO(results.get(i), i + 1))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(leaderboard, "Leaderboard fetched successfully"));
    }
}
