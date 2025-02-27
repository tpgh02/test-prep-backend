package com.test_prep_ai.backend.problem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponse {
    private long projectId;
    private long problemId;
    private Boolean isCorrect;
}
