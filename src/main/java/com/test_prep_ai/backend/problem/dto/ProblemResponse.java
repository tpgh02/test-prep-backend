package com.test_prep_ai.backend.problem.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ProblemResponse {
    private Long problemId;         // 문제 ID
    private String problemTitle;    // 문제 제목(객관식, 단답형, 서술형 등 공통)
    private String problemType;     // "객관식", "단답형", "서술형"
    private Map<String, String> options; // 객관식 선택지 (단답형, 서술형일 때는 빈 map or null)
    private String answer;          // 정답
    private String description;     // 문제 해설(설명)
}