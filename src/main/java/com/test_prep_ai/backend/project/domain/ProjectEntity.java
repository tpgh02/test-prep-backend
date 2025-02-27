package com.test_prep_ai.backend.project.domain;

import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.problem.domain.ProblemEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProblemEntity> problems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    public ProjectEntity(String name, MemberEntity member) {
        this.name = name;
        this.member = member;
    }
}
