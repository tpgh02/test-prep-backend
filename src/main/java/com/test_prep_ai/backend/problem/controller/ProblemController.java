package com.test_prep_ai.backend.problem.controller;

import com.test_prep_ai.backend.problem.dto.AnswerRequest;
import com.test_prep_ai.backend.problem.dto.AnswerResponse;
import com.test_prep_ai.backend.problem.dto.ProblemListResponse;
import com.test_prep_ai.backend.problem.service.ProblemService;
import com.test_prep_ai.backend.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/{projectId}")
    public DataResponse<ProblemListResponse> getProblemList(@PathVariable long projectId) {
        ProblemListResponse response = problemService.getProblemListResponse(projectId);

        return DataResponse.of(response);
    }

    @PostMapping("/{projectId}/{problemId}")
    public DataResponse<AnswerResponse> isCorrect(@PathVariable long projectId, @PathVariable long problemId, @RequestBody AnswerRequest answerRequest) {
        AnswerResponse response = problemService.isCorrectAnswer(projectId, problemId, answerRequest.getAnswer());
        return DataResponse.of(response);
    }
}
