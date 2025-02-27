package com.test_prep_ai.backend.fastapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test_prep_ai.backend.exception.NotFoundException;
import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.member.repository.MemberRepository;
import com.test_prep_ai.backend.problem.service.ProblemService;
import com.test_prep_ai.backend.project.domain.ProjectEntity;
import com.test_prep_ai.backend.project.service.ProjectService;
import com.test_prep_ai.backend.question.dto.QuestionResponse;
import com.test_prep_ai.backend.question.service.QuestionService;
import com.test_prep_ai.backend.security.Session;
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
    private final ProjectService projectService;
    private final MemberRepository memberRepository;
    private final ProblemService problemService;
    private String url = "http://localhost:8000/problems";

    public long connectWithAI(List<String> types, String level, String message, String fileName, Session session) throws JsonProcessingException {
        QuestionResponse questionResponse = questionService.createQuestionResponse(stringToIntegerList(types), level, message, fileName);
        MemberEntity member = memberRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        url += "/input-pdf";

        // FastAPI에 요청보내기
        HttpEntity<QuestionResponse> requestEntity = new HttpEntity<>(questionResponse, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // 응답 json을 dto로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        FastApiResponse apiResponse = objectMapper.readValue(response.getBody(), FastApiResponse.class);

        // 새 프로젝트 생성
        ProjectEntity project = projectService.createProjectEntity(member.getUsername() + "의 프로젝트", member);

        // 응답 dto로 문제 엔터티 생성
        problemService.createProblemEntity(apiResponse.getQuestions(), project);

        System.out.println(response.getBody());

        return project.getId();
    }

    private List<Integer> stringToIntegerList(List<String> strings) {
        return strings.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
