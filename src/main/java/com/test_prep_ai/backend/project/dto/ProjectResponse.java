package com.test_prep_ai.backend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {

    private long currentProjectId;
    private List<ProjectList> projectList;
}
