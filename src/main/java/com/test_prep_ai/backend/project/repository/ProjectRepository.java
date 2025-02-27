package com.test_prep_ai.backend.project.repository;

import com.test_prep_ai.backend.project.domain.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

}
