package com.test_prep_ai.backend.problem.service;

import com.test_prep_ai.backend.exception.NotFoundException;
import com.test_prep_ai.backend.problem.domain.ProblemEntity;
import com.test_prep_ai.backend.problem.dto.AnswerResponse;
import com.test_prep_ai.backend.problem.dto.ProblemListResponse;
import com.test_prep_ai.backend.problem.dto.ProblemResponse;
import com.test_prep_ai.backend.problem.dto.ProblemType;
import com.test_prep_ai.backend.problem.repository.ProblemRepository;
import com.test_prep_ai.backend.project.domain.ProjectEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    @Transactional
    public void createProblemEntity(List<ProblemType> problems, ProjectEntity projectEntity) {

        for (ProblemType problem : problems) {
            String type = problem.getType();
            problem.getQuestions().forEach(problemResponse -> {
                ProblemEntity problemEntity = ProblemEntity.builder()
                        .title(problemResponse.getText())
                        .description(problemResponse.getExplanation())
                        .type(type)
                        .options(problemResponse.getOptions())
                        .project(projectEntity)
                        .answer(problemResponse.getAnswer())
                        .build();

                problemRepository.save(problemEntity);
            });
        }
    }


    private List<ProblemResponse> getProblems(long projectId) {
        List<ProblemEntity> problemEntities = problemRepository.findAllByProjectId(projectId);

        return problemEntities.stream()
                .map(entity -> ProblemResponse.builder()
                        .problemId(entity.getId())
                        .problemTitle(entity.getTitle()) // "문제 제목"
                        .problemType(entity.getType())   // "객관식"/"단답형"/"서술형" 등
                        .options(entity.getOptions())     // Map<String, String> 형태로 저장
                        .answer(entity.getAnswer())
                        .description(entity.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public ProblemListResponse getProblemListResponse(long projectId) {

        List<ProblemResponse> problemResponses = getProblems(projectId);

        return ProblemListResponse.builder()
                .projectId(projectId)
                .problemList(problemResponses)
                .build();
    }

    public AnswerResponse isCorrectAnswer(long projectId, long problemId, String userAnswer){
        ProblemEntity problem = problemRepository.findById(problemId).orElseThrow(() -> new NotFoundException("not found problem"));
        Boolean isCorrect = problem.getAnswer().equals(userAnswer);

//        if (problem.getType().equals("단답형")) {
//
//        }

        return AnswerResponse.builder()
                .projectId(projectId)
                .problemId(problemId)
                .isCorrect(isCorrect)
                .build();
    }
}
