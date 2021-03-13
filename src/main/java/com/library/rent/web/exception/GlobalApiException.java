package com.library.rent.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalApiException extends RuntimeException {

    private final String mesaage;
    private final String content;
    private final HttpStatus code;
    public GlobalApiException(ErrorCode errorCode, String content, HttpStatus code) {
        super(errorCode.getMessage());
        this.mesaage = errorCode.getMessage();
        this.content = content;
        this.code = code;
    }
}
