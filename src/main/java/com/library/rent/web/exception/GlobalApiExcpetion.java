package com.library.rent.web.exception;

import lombok.Getter;

@Getter
public class GlobalApiExcpetion extends RuntimeException{

    private final String mesaage;
    public GlobalApiExcpetion(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.mesaage = errorCode.getMessage();
    }
}
