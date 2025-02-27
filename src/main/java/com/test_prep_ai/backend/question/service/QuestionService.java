package com.test_prep_ai.backend.question.service;

import com.test_prep_ai.backend.question.domain.QuestionEntity;
import com.test_prep_ai.backend.question.dto.QuestionRequest;
import com.test_prep_ai.backend.question.dto.QuestionResponse;
import com.test_prep_ai.backend.util.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final S3Service s3Service;

    public void createQuestionRequest(QuestionRequest questionRequest) {
        QuestionEntity.builder()
                .level(questionRequest.getLevel())
                .types(questionRequest.getTypes())
                .message(questionRequest.getMessage())
                .build();
    }

    public String sendToS3(MultipartFile file) {
        return s3Service.uploadFile(file);
    }

    public QuestionResponse createQuestionResponse(List<Integer> types, String level, String message, String fileName) {
        return QuestionResponse.builder()
                .level(level)
                .types(types)
                .message(message)
                .fileName(fileName)
                .build();
    }
}
