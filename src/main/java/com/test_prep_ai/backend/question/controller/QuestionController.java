package com.test_prep_ai.backend.question.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.fastapi.FastAPIService;
import com.test_prep_ai.backend.project.dto.ProjectIdResponse;
import com.test_prep_ai.backend.project.service.ProjectService;
import com.test_prep_ai.backend.question.service.QuestionService;
import com.test_prep_ai.backend.response.DataResponse;
import com.test_prep_ai.backend.security.CurrentSession;
import com.test_prep_ai.backend.security.Session;
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
    private final ProjectService projectService;

    @PostMapping("/question")
    public DataResponse<ProjectIdResponse> createQuestion(@RequestParam List<String> types,
                                                          @RequestParam String level,
                                                          @RequestParam String message,
                                                          @RequestPart MultipartFile file,
                                                          @CurrentSession Session session) throws JsonProcessingException {

        String fileName = questionService.sendToS3(file);

        long projectId = fastAPIService.connectWithAI(types, level, message, fileName, session);
        ProjectIdResponse projectIdResponse = projectService.createProjectIdResponse(projectId);
        return DataResponse.of(projectIdResponse);
    }
}
