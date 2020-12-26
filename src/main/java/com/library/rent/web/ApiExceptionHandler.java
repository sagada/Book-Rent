package com.library.rent.web;

import com.library.rent.web.exception.DuplicateBookException;
import com.library.rent.web.exception.ErrorBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler{

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ErrorBody> handleDuplicateException(DuplicateBookException ex)
    {
        log.error("DuplicateBookException : {}", ex.getLocalizedMessage());

        ErrorBody errorBody = new ErrorBody();
        errorBody.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorBody.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorBody> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        log.error("MethodArgumentNotValidException : {}", ex.getLocalizedMessage());
        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors())
        {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
        }

        ErrorBody errorBody = new ErrorBody();
        errorBody.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorBody.setMessage(ex.getMessage());
        errorBody.setContent(builder.toString());

        return ResponseEntity.badRequest().body(errorBody);
    }

}
