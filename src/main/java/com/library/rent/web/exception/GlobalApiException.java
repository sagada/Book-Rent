package com.library.rent.web.exception;

import lombok.Getter;

@Getter
public class GlobalApiException extends RuntimeException{

    private final String mesaage;
    private final String content;

    public GlobalApiException(ErrorCode errorCode, String content)
    {
        super(errorCode.getMessage());
        this.mesaage = errorCode.getMessage();
        this.content = content;
    }
}
