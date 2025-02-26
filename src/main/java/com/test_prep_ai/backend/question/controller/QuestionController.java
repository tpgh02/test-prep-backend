package com.test_prep_ai.backend.question.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.question.dto.QuestionRequest;
import com.test_prep_ai.backend.question.service.QuestionService;
import com.test_prep_ai.backend.response.StatusResponse;
import com.test_prep_ai.backend.security.CurrentSession;
import com.test_prep_ai.backend.security.Session;
import com.test_prep_ai.backend.util.FastAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final FastAPIService fastAPIService;

    @PostMapping("/question")
    public StatusResponse createQuestion(@RequestBody QuestionRequest questionRequest, @CurrentSession Session session) throws JsonProcessingException {
        questionService.createQuestionRequest(questionRequest, session);
        String fileName = questionService.sendToS3(questionRequest.getFile());

        fastAPIService.sendToAI(questionRequest, fileName);

        return StatusResponse.of(200, "OK");
    }
}
