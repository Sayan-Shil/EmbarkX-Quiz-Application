package com.embarkx.embarkxquiz.repositories.session;

import com.embarkx.embarkxquiz.models.session.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findBySessionId(Long sessionId);

    List<Result> findByStudentId(Long studentId);

    List<Result> findByQuizId(Long quizId);

    @Query("SELECT r FROM Result r WHERE r.quiz.id = :quizId ORDER BY r.percentage DESC, r.timeTakenMinutes ASC")
    List<Result> findLeaderboardByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT r FROM Result r WHERE r.student.id = :studentId AND r.quiz.id = :quizId ORDER BY r.createdAt DESC")
    List<Result> findByStudentIdAndQuizId(@Param("studentId") Long studentId, @Param("quizId") Long quizId);
}
