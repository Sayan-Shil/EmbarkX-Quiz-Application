package com.embarkx.embarkxquiz.repositories.session;

import com.embarkx.embarkxquiz.models.session.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findBySessionId(Long sessionId);

    @Query("SELECT a FROM Answer a WHERE a.session.id = :sessionId AND a.question.id = :questionId")
    Optional<Answer> findBySessionIdAndQuestionId(@Param("sessionId") Long sessionId, @Param("questionId") Long questionId);

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.session.id = :sessionId AND a.isCorrect = true")
    Long countCorrectAnswersBySessionId(@Param("sessionId") Long sessionId);

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.session.id = :sessionId AND a.isCorrect = false")
    Long countWrongAnswersBySessionId(@Param("sessionId") Long sessionId);
}
