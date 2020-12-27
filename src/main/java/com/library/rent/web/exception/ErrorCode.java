package com.library.rent.web.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATE_BOOK("중복된 값 또는 데이터가 있습니다.");

    private final String message;

    ErrorCode(String message)
    {
        this.message = message;
    }

}
