package com.embarkx.embarkxquiz.repositories.session;

import com.embarkx.embarkxquiz.enums.SessionStatus;
import com.embarkx.embarkxquiz.models.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByStudentId(Long studentId);

    List<Session> findByQuizId(Long quizId);

    List<Session> findByStatus(SessionStatus status);

    @Query("SELECT s FROM Session s WHERE s.student.id = :studentId AND s.quiz.id = :quizId")
    List<Session> findByStudentIdAndQuizId(@Param("studentId") Long studentId, @Param("quizId") Long quizId);

    @Query("SELECT s FROM Session s WHERE s.student.id = :studentId AND s.quiz.id = :quizId AND s.status = :status")
    Optional<Session> findByStudentIdAndQuizIdAndStatus(
            @Param("studentId") Long studentId,
            @Param("quizId") Long quizId,
            @Param("status") SessionStatus status);

    @Query("SELECT COUNT(s) FROM Session s WHERE s.student.id = :studentId AND s.quiz.id = :quizId AND s.status = 'COMPLETED'")
    Long countCompletedSessionsByStudentAndQuiz(@Param("studentId") Long studentId, @Param("quizId") Long quizId);

    @Query("SELECT s FROM Session s LEFT JOIN FETCH s.answers WHERE s.id = :sessionId")
    Optional<Session> findByIdWithAnswers(@Param("sessionId") Long sessionId);

}
