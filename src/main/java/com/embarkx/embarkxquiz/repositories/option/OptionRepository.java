package com.embarkx.embarkxquiz.repositories.option;

import com.embarkx.embarkxquiz.models.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestionId(Long questionId);

    @Query("SELECT o FROM Option o WHERE o.question.id = :questionId ORDER BY o.orderIndex ASC")
    List<Option> findByQuestionIdOrderByOrderIndex(@Param("questionId") Long questionId);

    @Query("SELECT o FROM Option o WHERE o.question.id = :questionId AND o.isCorrect = true")
    List<Option> findCorrectOptionsByQuestionId(@Param("questionId") Long questionId);
}
