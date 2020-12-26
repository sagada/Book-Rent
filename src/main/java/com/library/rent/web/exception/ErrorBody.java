package com.library.rent.web.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorBody {

    private String message;
    private HttpStatus httpStatus;
    private String content;
    public ErrorBody()
    {

    }
    @Builder
    public ErrorBody(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
