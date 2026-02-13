package com.embarkx.embarkxquiz.models.option;

import com.embarkx.embarkxquiz.models.common.BaseEntity;
import com.embarkx.embarkxquiz.models.question.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, length = 500)
    private String optionText;

    @Column(nullable = false)
    private Boolean isCorrect = false;

    @Column(name = "order_index")
    private Integer orderIndex;
}
