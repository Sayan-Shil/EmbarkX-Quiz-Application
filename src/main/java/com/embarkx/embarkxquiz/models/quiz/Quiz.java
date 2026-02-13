package com.embarkx.embarkxquiz.models.quiz;

import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import com.embarkx.embarkxquiz.models.common.AuditableEntity;
import com.embarkx.embarkxquiz.models.question.Question;
import com.embarkx.embarkxquiz.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz extends AuditableEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizDifficulty difficulty;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Integer passingScore;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Boolean isPublished = false;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Question> questions = new ArrayList<>();

    @Column(name = "category")
    private String category;

    @Column(name = "instructions", length = 2000)
    private String instructions;

    @Column(name = "max_attempts")
    private Integer maxAttempts = 1;

    @Column(name = "shuffle_questions")
    private Boolean shuffleQuestions = false;

    @Column(name = "show_results_immediately")
    private Boolean showResultsImmediately = true;
}
