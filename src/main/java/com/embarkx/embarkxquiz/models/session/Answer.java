package com.embarkx.embarkxquiz.models.session;

import com.embarkx.embarkxquiz.models.common.BaseEntity;
import com.embarkx.embarkxquiz.models.option.Option;
import com.embarkx.embarkxquiz.models.question.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id")
    private Option selectedOption;

    @Column(name = "text_answer", length = 2000)
    private String textAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "marks_obtained")
    private Integer marksObtained;

    @Column(name = "time_spent_seconds")
    private Integer timeSpentSeconds;

}
