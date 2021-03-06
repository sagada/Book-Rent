package com.library.rent.web.member.dto;

import com.library.rent.web.member.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberSignUpResponseDto {
    private String email;
    private String nickname;
    private String message;

    @Builder
    public MemberSignUpResponseDto(String email, String nickname, String message) {
        this.email = email;
        this.nickname = nickname;
        this.message = message;
    }

    public static MemberSignUpResponseDto Ok(Member member)
    {
        return MemberSignUpResponseDto.builder().email(member.getEmail()).nickname(member.getNickname()).message("가입완료").build();
    }

}
