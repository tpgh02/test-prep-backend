package com.test_prep_ai.backend.question.service;

import com.test_prep_ai.backend.question.domain.QuestionEntity;
import com.test_prep_ai.backend.question.dto.QuestionRequest;
import com.test_prep_ai.backend.question.dto.QuestionResponse;
import com.test_prep_ai.backend.security.Session;
import com.test_prep_ai.backend.util.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final S3Service s3Service;

    public void createQuestionRequest(QuestionRequest questionRequest, Session session) {
        QuestionEntity.builder()
                .level(questionRequest.getLevel())
                .types(questionRequest.getTypes())
                .message(questionRequest.getMessage())
                .build();
    }

    public String sendToS3(MultipartFile file) {
        return s3Service.uploadFile(file);
    }

    public QuestionResponse createQuestionResponse(QuestionRequest questionRequest, String fileName) {
        return QuestionResponse.builder()
                .level(questionRequest.getLevel())
                .types(questionRequest.getTypes())
                .message(questionRequest.getMessage())
                .fileName(fileName)
                .build();
    }
}
