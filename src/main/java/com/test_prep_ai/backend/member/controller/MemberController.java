package com.test_prep_ai.backend.member.controller;

import com.test_prep_ai.backend.member.dto.LoginRequest;
import com.test_prep_ai.backend.member.dto.MemberInfoResponse;
import com.test_prep_ai.backend.member.dto.SignUpRequest;
import com.test_prep_ai.backend.member.service.MemberInfoService;
import com.test_prep_ai.backend.member.service.SignUpService;
import com.test_prep_ai.backend.response.DataResponse;
import com.test_prep_ai.backend.security.CurrentSession;
import com.test_prep_ai.backend.security.Session;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final SignUpService signUpService;
    private final MemberInfoService memberInfoService;


    @PostMapping("auth/signup")
    public DataResponse<MemberInfoResponse> localSignUp(@Valid @RequestBody SignUpRequest request) {
        MemberInfoResponse response = signUpService.signUp(request);
        return DataResponse.of(response);
    }

    @GetMapping("/user/info")
    public DataResponse<LoginRequest> getUserInfo(@CurrentSession Session session) {
        LoginRequest loginRequest = memberInfoService.createLoginRequest(session);
        return DataResponse.of(loginRequest);
    }
}
