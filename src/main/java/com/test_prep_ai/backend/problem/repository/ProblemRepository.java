package com.test_prep_ai.backend.problem.repository;

import com.test_prep_ai.backend.problem.domain.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {
    Optional<ProblemEntity> findById(Long id);
    List<ProblemEntity> findAllByProjectId(Long projectId);

}
