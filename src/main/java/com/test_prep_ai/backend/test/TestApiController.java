package com.test_prep_ai.backend.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.fastapi.FastApiResponse;
import com.test_prep_ai.backend.project.dto.ProjectResponse;
import com.test_prep_ai.backend.project.service.ProjectService;
import com.test_prep_ai.backend.response.DataResponse;
import com.test_prep_ai.backend.security.CurrentSession;
import com.test_prep_ai.backend.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/questions")
public class TestApiController {
    private final TestApiService testApiService;
    private final ProjectService projectService;

    @PostMapping("/question")
    public DataResponse<ProjectResponse> createQuestion(@RequestBody FastApiResponse fastApiResponse,
                                                        @CurrentSession Session session) throws JsonProcessingException {

        long projectId = testApiService.connectWithAITest(fastApiResponse, session);
        ProjectResponse projectResponse = projectService.createProjectResponse(projectId, session);
        return DataResponse.of(projectResponse);
    }
}
