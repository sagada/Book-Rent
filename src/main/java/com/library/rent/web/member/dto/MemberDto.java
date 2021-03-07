package com.library.rent.web.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    @NotNull
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100, message = "비밀번호는 3글자 이상입니다.")
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;
}
