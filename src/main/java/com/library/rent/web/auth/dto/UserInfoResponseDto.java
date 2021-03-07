package com.library.rent.web.auth.dto;

import lombok.Data;

@Data
public class UserInfoResponseDto {
    private String email;
    private String nickname;
    private String token;

    public UserInfoResponseDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
