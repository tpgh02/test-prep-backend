package com.test_prep_ai.backend.member.service;

import com.test_prep_ai.backend.exception.NotFoundException;
import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.member.dto.LoginRequest;
import com.test_prep_ai.backend.member.repository.MemberRepository;
import com.test_prep_ai.backend.project.dto.ProjectList;
import com.test_prep_ai.backend.project.service.ProjectService;
import com.test_prep_ai.backend.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberRepository memberRepository;
    private final ProjectService projectService;

    public LoginRequest createLoginRequest(Session session) {
        MemberEntity member = memberRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("Member not found"));

        List<ProjectList> projectList = projectService.getProjectList(session);

        return LoginRequest.builder()
                .username(member.getUsername())
                .projectList(projectList)
                .build();
    }
}
