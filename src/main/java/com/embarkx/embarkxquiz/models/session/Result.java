package com.embarkx.embarkxquiz.models.session;

import com.embarkx.embarkxquiz.models.common.BaseEntity;
import com.embarkx.embarkxquiz.models.option.Option;
import com.embarkx.embarkxquiz.models.question.Question;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import com.embarkx.embarkxquiz.models.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "score_obtained", nullable = false)
    private Integer scoreObtained;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @Column(name = "is_passed", nullable = false)
    private Boolean isPassed;

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Column(name = "correct_answers")
    private Integer correctAnswers;

    @Column(name = "wrong_answers")
    private Integer wrongAnswers;

    @Column(name = "unanswered")
    private Integer unanswered;

    @Column(name = "time_taken_minutes")
    private Integer timeTakenMinutes;

    @Column(name = "`rank`")
    private Integer rank;

    @Column(length = 1000)
    private String feedback;
}
