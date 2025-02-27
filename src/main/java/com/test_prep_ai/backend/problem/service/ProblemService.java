package com.test_prep_ai.backend.problem.service;

import com.test_prep_ai.backend.problem.domain.ProblemEntity;
import com.test_prep_ai.backend.problem.dto.ProblemType;
import com.test_prep_ai.backend.problem.repository.ProblemRepository;
import com.test_prep_ai.backend.project.domain.ProjectEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                        .build();

                problemRepository.save(problemEntity);
            });
        }
    }


}
