package com.embarkx.embarkxquiz.services.session;

import com.embarkx.embarkxquiz.dto.request.session.ResultResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.SessionResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.StartSessionRequestDTO;
import com.embarkx.embarkxquiz.dto.request.session.SubmitAnswerRequestDTO;

import java.util.List;

public interface SessionService {
    SessionResponseDTO startSession(StartSessionRequestDTO request, Long studentId);
    SessionResponseDTO submitAnswer(SubmitAnswerRequestDTO request);
    ResultResponseDTO submitSession(Long sessionId);
    SessionResponseDTO getSessionById(Long sessionId);
    List<SessionResponseDTO> getSessionsByStudent(Long studentId);
    List<SessionResponseDTO> getSessionsByQuiz(Long quizId);
    SessionResponseDTO getCurrentSession(Long studentId, Long quizId);
}
