package com.test_prep_ai.backend.member.dto;

import com.test_prep_ai.backend.project.dto.ProjectList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private List<ProjectList> projectList;
}
