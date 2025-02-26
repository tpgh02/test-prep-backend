package com.test_prep_ai.backend.member.controller;

import com.test_prep_ai.backend.exception.BadRequestException;
import com.test_prep_ai.backend.security.CurrentSession;
import com.test_prep_ai.backend.security.Session;
import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.member.repository.MemberRepository;
import com.test_prep_ai.backend.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/auth/test")
    public StatusResponse authTest() {
        return StatusResponse.of(200, "test success");
    }

    @GetMapping("/test")
    public StatusResponse test(@CurrentSession Session session) {
        return StatusResponse.of(200, "id : " + session.getId() + ", username : " + session.getUsername());
    }

    @GetMapping("/auth/create-member")
    public String createMember() {

        MemberEntity memberEntity = MemberEntity.builder()
                .username("test")
                .password(bCryptPasswordEncoder.encode("test1234!!"))
                .nickname("test")
                .build();

        memberRepository.save(memberEntity);
        return "Test User Created";
    }

    @GetMapping("/auth/exception")
    public void exceptionTest() {
        throw new BadRequestException("Bad Request");
    }

}
