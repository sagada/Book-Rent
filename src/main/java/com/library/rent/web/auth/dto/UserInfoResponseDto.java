package com.library.rent.web.auth.dto;

import com.library.rent.web.auth.Authority;
import lombok.Data;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserInfoResponseDto {
    private String email;
    private String nickname;
    private String token;
    private String role;

    public UserInfoResponseDto(String email, String nickname, Set<Authority> role) {
        this.email = email;
        this.nickname = nickname;
        this.role = role.isEmpty() ? "" : role.stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.joining(" "));
    }
}
