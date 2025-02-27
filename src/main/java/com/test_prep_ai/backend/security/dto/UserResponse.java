package com.test_prep_ai.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponse {
    private TokenResponse tokenResponse;
    private String username;
    private List<Object> projectList;
}
