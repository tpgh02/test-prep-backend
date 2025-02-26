package com.test_prep_ai.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.question.dto.QuestionRequest;
import com.test_prep_ai.backend.question.dto.QuestionResponse;
import com.test_prep_ai.backend.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FastAPIService {

    private final QuestionService questionService;
    private final String url = "http://localhost:8000/problems/pdf";

    public void sendToAI(QuestionRequest questionRequest, String fileName) throws JsonProcessingException {
        QuestionResponse questionResponse = questionService.createQuestionResponse(questionRequest, fileName);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<QuestionResponse> requestEntity = new HttpEntity<>(questionResponse, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        System.out.println(response.getBody());
    }
}
