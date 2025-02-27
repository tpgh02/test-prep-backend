package com.test_prep_ai.backend.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test_prep_ai.backend.exception.NotFoundException;
import com.test_prep_ai.backend.fastapi.FastApiResponse;
import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.member.repository.MemberRepository;
import com.test_prep_ai.backend.problem.service.ProblemService;
import com.test_prep_ai.backend.project.domain.ProjectEntity;
import com.test_prep_ai.backend.project.service.ProjectService;
import com.test_prep_ai.backend.question.service.QuestionService;
import com.test_prep_ai.backend.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestApiService {

    private final QuestionService questionService;
    private final ProjectService projectService;
    private final MemberRepository memberRepository;
    private final ProblemService problemService;

    public long connectWithAITest(FastApiResponse apiResponse, Session session) throws JsonProcessingException {

        MemberEntity member = memberRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));;

        // 새 프로젝트 생성
        ProjectEntity project = projectService.createProjectEntity(member.getUsername() + "의 프로젝트", member);

        // 응답 dto로 문제 엔터티 생성
        problemService.createProblemEntity(apiResponse.getQuestions_set(), project);

        System.out.println(apiResponse.getQuestions_set());

        return project.getId();
    }

    private List<Integer> stringToIntegerList(List<String> strings) {
        return strings.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

}
