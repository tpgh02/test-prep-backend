package com.test_prep_ai.backend.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    private int number;
    private String text; // title
    private String answer;
    private String explanation; // description
    private Map<String, String> options;
}
