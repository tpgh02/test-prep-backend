package com.test_prep_ai.backend.problem.domain;

import com.test_prep_ai.backend.project.domain.ProjectEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
public class ProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private String type;

    @ElementCollection
    private Map<String, String> options;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Builder
    public ProblemEntity(String title, String description, String type, Map<String, String> options, ProjectEntity project) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.options = options;
        this.project = project;
    }

}
