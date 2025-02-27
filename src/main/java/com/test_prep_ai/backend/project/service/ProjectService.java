package com.test_prep_ai.backend.project.service;

import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.project.domain.ProjectEntity;
import com.test_prep_ai.backend.project.dto.ProjectIdResponse;
import com.test_prep_ai.backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectEntity createProjectEntity(String name, MemberEntity member) {
        ProjectEntity projectEntity = ProjectEntity.builder()
                    .name(name)
                    .member(member)
                    .build();
        projectRepository.save(projectEntity);
        return projectEntity;
    }

    public ProjectIdResponse createProjectIdResponse(long projectId) {
        return ProjectIdResponse.builder()
                .currentProjectId(projectId)
                .build();
    }
}
