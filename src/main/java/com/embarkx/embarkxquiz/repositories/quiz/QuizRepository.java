package com.embarkx.embarkxquiz.repositories.quiz;

import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByIsActive(Boolean isActive);

    List<Quiz> findByIsPublished(Boolean isPublished);

    List<Quiz> findByDifficulty(QuizDifficulty difficulty);

    List<Quiz> findByCategory(String category);

    @Query("SELECT q FROM Quiz q WHERE q.createdByUser.id = :userId")
    List<Quiz> findByCreatedByUserId(@Param("userId") Long userId);

    @Query("SELECT q FROM Quiz q WHERE q.isPublished = true AND q.isActive = true")
    List<Quiz> findAllPublishedAndActive();

    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questions WHERE q.id = :quizId")
    Optional<Quiz> findByIdWithQuestions(@Param("quizId") Long quizId);

    @Query("SELECT q FROM Quiz q WHERE LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Quiz> searchByTitle(@Param("keyword") String keyword);
}
