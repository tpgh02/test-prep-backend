package com.test_prep_ai.backend.member.service;

import com.test_prep_ai.backend.exception.BadRequestException;
import com.test_prep_ai.backend.member.domain.MemberEntity;
import com.test_prep_ai.backend.member.dto.MemberInfoResponse;
import com.test_prep_ai.backend.member.dto.SignUpRequest;
import com.test_prep_ai.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public MemberInfoResponse signUp(SignUpRequest data) {
        // member 중복 체크
        if (memberRepository.existsByUsername(data.getUsername()))
            throw new BadRequestException("User is already registered.");

        // password 체크
        passwordCheck(data.getPassword1(), data.getPassword2());

        // nickname 체크
        // nicknameCheck(data.getNickname());

        // entity로 변환
        MemberEntity memberEntity = MemberEntity.builder()
                .username(data.getUsername())
                .password(bCryptPasswordEncoder.encode(data.getPassword1()))
                .nickname(data.getNickname())
                .build();

        // 저장
        memberRepository.save(memberEntity);

        // 생성한 회원정보 return
        return returnMemberInfo(memberEntity);
    }

//    private void nicknameCheck(String nickname) {
//
//        String regex = "^[가-힣a-zA-Z0-9]{1,10}$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(nickname);
//
//        if (!matcher.matches()) {
//            throw new BadRequestException("The nickname must be 1 to 10 characters, consisting only of English, numbers, and Korean.");
//        }
//
//    }

    private void passwordCheck(String pw1, String pw2) {
        if (!pw1.equals(pw2))
            throw new BadRequestException("Passwords do not match.");

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pw1);

        if (!matcher.matches()) {
            throw new BadRequestException("The password must be 8 to 16 characters, including all English, numbers, and special characters.");
        }
    }

    private MemberInfoResponse returnMemberInfo(MemberEntity memberEntity) {
        return MemberInfoResponse.builder()
                .id(memberEntity.getId())
                .username(memberEntity.getUsername())
                .nickname(memberEntity.getNickname())
                .build();
    }

}
