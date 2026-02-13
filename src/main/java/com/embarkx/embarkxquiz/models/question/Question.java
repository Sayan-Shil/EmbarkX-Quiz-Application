package com.embarkx.embarkxquiz.models.question;

import com.embarkx.embarkxquiz.enums.QuestionType;
import com.embarkx.embarkxquiz.models.common.AuditableEntity;
import com.embarkx.embarkxquiz.models.common.BaseEntity;
import com.embarkx.embarkxquiz.models.option.Option;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(nullable = false)
    private Integer marks;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(length = 2000)
    private String explanation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Option> options = new ArrayList<>();

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_required")
    private Boolean isRequired = true;
}
