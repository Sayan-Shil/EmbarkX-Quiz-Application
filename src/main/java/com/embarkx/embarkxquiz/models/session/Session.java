package com.embarkx.embarkxquiz.models.session;

import com.embarkx.embarkxquiz.enums.SessionStatus;
import com.embarkx.embarkxquiz.models.common.BaseEntity;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import com.embarkx.embarkxquiz.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "submit_time")
    private LocalDateTime submitTime;

    @Column(name = "time_taken_minutes")
    private Integer timeTakenMinutes;

    @Column(name = "score_obtained")
    private Integer scoreObtained;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "is_passed")
    private Boolean isPassed;

    @Column(name = "attempt_number")
    private Integer attemptNumber = 1;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    @OneToOne(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Result result;
}
