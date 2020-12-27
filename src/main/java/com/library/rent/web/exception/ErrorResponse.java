package com.library.rent.web.exception;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ErrorResponse {

    private static final String DUPLICATE_DATA = "Duplicate Data";
    private int statusCode;
    private String message;
    private String content;

    public ErrorResponse(String message, int statusCode)
    {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ErrorResponse(String message, String content, int statusCode)
    {
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
    }
}
