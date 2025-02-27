package com.test_prep_ai.backend.question.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.question.service.QuestionService;
import com.test_prep_ai.backend.response.StatusResponse;
import com.test_prep_ai.backend.util.FastAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final FastAPIService fastAPIService;

    @PostMapping("/question")
    public StatusResponse createQuestion(@RequestParam List<String> types,
                                         @RequestParam String level,
                                         @RequestParam String message,
                                         @RequestPart MultipartFile file) throws JsonProcessingException {

        String fileName = questionService.sendToS3(file);

        fastAPIService.sendToAI(types, level, message, fileName);

        return StatusResponse.of(200, "OK");
    }
}
