package com.library.rent.web.member.dto;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotNull
    @Size(min = 3, max = 100, message = "비밀번호는 3글자 이상이어야 합니다.")
    private String password;
}
