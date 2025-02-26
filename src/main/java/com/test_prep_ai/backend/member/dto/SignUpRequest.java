package com.test_prep_ai.backend.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "Username is Required")
    // @Email(message = "Username is out form")  // username을 이메일로 쓸 거면 Email Validation 쓰기
    private String username;

    @NotEmpty(message = "password is Required")
    private String password1;
    @NotEmpty(message = "password confirmation is Required")
    private String password2;

    @NotEmpty(message = "Nickname is Required")
    private String nickname;

}
