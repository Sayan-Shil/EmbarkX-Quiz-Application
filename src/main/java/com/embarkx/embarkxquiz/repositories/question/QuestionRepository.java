package com.embarkx.embarkxquiz.repositories.question;

import com.embarkx.embarkxquiz.enums.QuestionType;
import com.embarkx.embarkxquiz.models.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuizId(Long quizId);

    List<Question> findByQuestionType(QuestionType questionType);

    @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.orderIndex ASC")
    List<Question> findByQuizIdOrderByOrderIndex(@Param("quizId") Long quizId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.quiz.id = :quizId")
    Long countByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT SUM(q.marks) FROM Question q WHERE q.quiz.id = :quizId")
    Integer sumMarksByQuizId(@Param("quizId") Long quizId);

    @Query("""
    SELECT q FROM Question q
    WHERE q.quiz.id = :quizId
    AND q.id IN :questionIds
""")
    List<Question> findAllById(List<Long> questionIds);
}
