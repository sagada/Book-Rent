package com.library.rent.web.auth;

import com.library.rent.web.exception.ErrorCode;
import com.library.rent.web.exception.GlobalApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class ExceptionController {

    @GetMapping(value = "/exception/autherror")
    public void autherror()
    {
        System.out.println("autherror");
        throw new GlobalApiException(ErrorCode.NON_ROLES, "로그인 에러", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/exception/accessdined")
    public void accessdined()
    {
        System.out.println("accessdined");
        throw new GlobalApiException(ErrorCode.NON_AUTHORITIES, "권한 에러", HttpStatus.FORBIDDEN);
    }
}
