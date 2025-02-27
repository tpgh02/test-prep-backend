package com.test_prep_ai.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.question.dto.QuestionResponse;
import com.test_prep_ai.backend.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FastAPIService {

    private final QuestionService questionService;
    private String url = "http://localhost:8000/problems";

    public void sendToAI(List<String> types, String level, String message, String fileName) throws JsonProcessingException {
        QuestionResponse questionResponse = questionService.createQuestionResponse(stringToIntegerList(types), level, message, fileName);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        url += "/input-pdf";

        //FastAPI에 요청보내기
        HttpEntity<QuestionResponse> requestEntity = new HttpEntity<>(questionResponse, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);



        System.out.println(response.getBody());
    }

    private List<Integer> stringToIntegerList(List<String> strings) {
        return strings.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
