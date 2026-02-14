package com.embarkx.embarkxquiz.mapper;

import com.embarkx.embarkxquiz.dto.request.session.LeaderboardResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.ResultResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.SessionResponseDTO;
import com.embarkx.embarkxquiz.models.session.Result;
import com.embarkx.embarkxquiz.models.session.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {
    public SessionResponseDTO toResponseDTO(Session session) {
        if (session == null) {
            return null;
        }

        return SessionResponseDTO.builder()
                .id(session.getId())
                .quizId(session.getQuiz() != null ? session.getQuiz().getId() : null)
                .quizTitle(session.getQuiz() != null ? session.getQuiz().getTitle() : null)
                .studentId(session.getStudent() != null ? session.getStudent().getId() : null)
                .studentName(session.getStudent() != null ?
                        session.getStudent().getFirstName() + " " + session.getStudent().getLastName() : null)
                .status(session.getStatus())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .submitTime(session.getSubmitTime())
                .timeTakenMinutes(session.getTimeTakenMinutes())
                .scoreObtained(session.getScoreObtained())
                .totalScore(session.getTotalScore())
                .percentage(session.getPercentage())
                .isPassed(session.getIsPassed())
                .attemptNumber(session.getAttemptNumber())
                .durationMinutes(session.getQuiz() != null ? session.getQuiz().getDurationMinutes() : null)
                .createdAt(session.getCreatedAt())
                .build();
    }

    public ResultResponseDTO toResultResponseDTO(Result result) {
        if (result == null) {
            return null;
        }

        return ResultResponseDTO.builder()
                .id(result.getId())
                .sessionId(result.getSession() != null ? result.getSession().getId() : null)
                .quizId(result.getQuiz() != null ? result.getQuiz().getId() : null)
                .quizTitle(result.getQuiz() != null ? result.getQuiz().getTitle() : null)
                .studentId(result.getStudent() != null ? result.getStudent().getId() : null)
                .studentName(result.getStudent() != null ?
                        result.getStudent().getFirstName() + " " + result.getStudent().getLastName() : null)
                .scoreObtained(result.getScoreObtained())
                .totalScore(result.getTotalScore())
                .percentage(result.getPercentage())
                .isPassed(result.getIsPassed())
                .totalQuestions(result.getTotalQuestions())
                .correctAnswers(result.getCorrectAnswers())
                .wrongAnswers(result.getWrongAnswers())
                .unanswered(result.getUnanswered())
                .timeTakenMinutes(result.getTimeTakenMinutes())
                .rank(result.getRank())
                .feedback(result.getFeedback())
                .createdAt(result.getCreatedAt())
                .build();
    }

    public LeaderboardResponseDTO toLeaderboardDTO(Result result, Integer rank) {
        if (result == null) {
            return null;
        }

        return LeaderboardResponseDTO.builder()
                .rank(rank)
                .studentId(result.getStudent() != null ? result.getStudent().getId() : null)
                .studentName(result.getStudent() != null ?
                        result.getStudent().getFirstName() + " " + result.getStudent().getLastName() : null)
                .scoreObtained(result.getScoreObtained())
                .totalScore(result.getTotalScore())
                .percentage(result.getPercentage())
                .timeTakenMinutes(result.getTimeTakenMinutes())
                .build();
    }
}
