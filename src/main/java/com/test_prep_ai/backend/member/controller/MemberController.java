package com.test_prep_ai.backend.member.controller;

import com.test_prep_ai.backend.member.dto.MemberInfoResponse;
import com.test_prep_ai.backend.member.dto.SignUpRequest;
import com.test_prep_ai.backend.member.service.SignUpService;
import com.test_prep_ai.backend.response.DataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final SignUpService signUpService;


    @PostMapping("auth/signup")
    public DataResponse<MemberInfoResponse> localSignUp(@Valid @RequestBody SignUpRequest request) {
        MemberInfoResponse response = signUpService.signUp(request);
        return DataResponse.of(response);
    }

}
