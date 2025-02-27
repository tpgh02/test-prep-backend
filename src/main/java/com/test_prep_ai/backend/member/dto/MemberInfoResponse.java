package com.test_prep_ai.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponse {

    private Long id;

    private String useremail;

    private String username;

}
