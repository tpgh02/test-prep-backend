package com.test_prep_ai.backend.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private List<Integer> types;
    private String level;
    private String message;
    private MultipartFile file;
}
