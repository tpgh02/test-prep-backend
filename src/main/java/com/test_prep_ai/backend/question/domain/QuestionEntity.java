package com.test_prep_ai.backend.question.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;
    private String level;

    @ElementCollection
    private List<Integer> types;

    @Builder
    public QuestionEntity(String message, String level, List<Integer> types) {
        this.message = message;
        this.level = level;
        this.types = types;
    }

}
