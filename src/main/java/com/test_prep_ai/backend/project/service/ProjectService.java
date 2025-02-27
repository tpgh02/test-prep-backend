package com.test_prep_ai.backend.project.service;

import com.test_prep_ai.backend.exception.NotFoundException;
import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.member.repository.MemberRepository;
import com.test_prep_ai.backend.project.domain.ProjectEntity;
import com.test_prep_ai.backend.project.dto.ProjectList;
import com.test_prep_ai.backend.project.dto.ProjectResponse;
import com.test_prep_ai.backend.project.repository.ProjectRepository;
import com.test_prep_ai.backend.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    public ProjectEntity createProjectEntity(String name, MemberEntity member) {
        ProjectEntity projectEntity = ProjectEntity.builder()
                    .name(name)
                    .member(member)
                    .build();
        projectRepository.save(projectEntity);
        return projectEntity;
    }

    public ProjectResponse createProjectResponse(long projectId, Session session) {

        List<ProjectList> projectList = getProjectList(session);

        return ProjectResponse.builder()
                .currentProjectId(projectId)
                .projectList(projectList)
                .build();
    }

    public List<ProjectList> getProjectList(Session session) {
        MemberEntity member = memberRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("Member not found"));

        return Optional.ofNullable(member.getProjects())
                .orElse(Collections.emptyList())  // null일 경우 빈 리스트 반환
                .stream()
                .map(project -> ProjectList.builder()
                        .projectId(project.getId())
                        .projectName(project.getName())
                        .build())
                .collect(Collectors.toList());
    }

}
