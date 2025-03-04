package com.test_prep_ai.backend.fastapi;

import com.test_prep_ai.backend.problem.dto.ProblemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FastApiResponse {
    private List<ProblemType> questions_set;
    private List<Integer> referencedPages;
    private String topic;
}
