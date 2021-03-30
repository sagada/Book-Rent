package com.library.rent.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalApiException extends RuntimeException {

    private String mesaage;
    private String content;
    private HttpStatus code;
    public GlobalApiException(ErrorCode errorCode, String content, HttpStatus code) {
        super(errorCode.getMessage());
        this.mesaage = errorCode.getMessage();
        this.content = content;
        this.code = code;
    }

    public GlobalApiException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
