package com.test_prep_ai.backend.problem.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProblemListResponse {
    private long projectId;
    private List<ProblemResponse> problemList;
}
