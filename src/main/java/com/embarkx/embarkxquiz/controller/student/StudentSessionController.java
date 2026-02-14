package com.embarkx.embarkxquiz.controller.student;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.session.ResultResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.SessionResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.StartSessionRequestDTO;
import com.embarkx.embarkxquiz.dto.request.session.SubmitAnswerRequestDTO;
import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.services.session.SessionService;
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
@RequestMapping("/api/student/sessions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
public class StudentSessionController {

    private final SessionService sessionService;
    private final UserService userService;

    @PostMapping("/start")

    public ResponseEntity<ApiResponse<SessionResponseDTO>> startSession(
            @Valid @RequestBody StartSessionRequestDTO request,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO currentUser = userService.getUserByEmail(userDetails.getUsername());

        SessionResponseDTO session = sessionService.startSession(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(session, MessageConstants.SESSION_STARTED_SUCCESS));
    }

    @PostMapping("/submit-answer")

    public ResponseEntity<ApiResponse<SessionResponseDTO>> submitAnswer(
            @Valid @RequestBody SubmitAnswerRequestDTO request) {

        SessionResponseDTO session = sessionService.submitAnswer(request);
        return ResponseEntity.ok(ApiResponse.success(session, MessageConstants.ANSWER_SUBMITTED_SUCCESS));
    }

    @PostMapping("/{sessionId}/submit")
    public ResponseEntity<ApiResponse<ResultResponseDTO>> submitSession(@PathVariable Long sessionId) {
        ResultResponseDTO result = sessionService.submitSession(sessionId);
        return ResponseEntity.ok(ApiResponse.success(result, MessageConstants.SESSION_COMPLETED_SUCCESS));
    }

    @GetMapping("/my-sessions")
    public ResponseEntity<ApiResponse<List<SessionResponseDTO>>> getMySessions(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO currentUser = userService.getUserByEmail(userDetails.getUsername());

        List<SessionResponseDTO> sessions = sessionService.getSessionsByStudent(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(sessions, "Sessions fetched successfully"));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<SessionResponseDTO>> getSessionById(@PathVariable Long sessionId) {
        SessionResponseDTO session = sessionService.getSessionById(sessionId);
        return ResponseEntity.ok(ApiResponse.success(session, "Session fetched successfully"));
    }

    @GetMapping("/current/quiz/{quizId}")
    public ResponseEntity<ApiResponse<SessionResponseDTO>> getCurrentSession(
            @PathVariable Long quizId,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO currentUser = userService.getUserByEmail(userDetails.getUsername());

        SessionResponseDTO session = sessionService.getCurrentSession(currentUser.getId(), quizId);
        return ResponseEntity.ok(ApiResponse.success(session, "Current session fetched successfully"));
    }
}
