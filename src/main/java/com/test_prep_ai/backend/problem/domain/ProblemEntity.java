package com.test_prep_ai.backend.problem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;
    private String description;

    @ElementCollection
    private List<String> choice;

    @Enumerated(EnumType.STRING)
    private ProblemType type;

    private String level;
}
