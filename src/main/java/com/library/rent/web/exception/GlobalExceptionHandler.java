package com.library.rent.web.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;
import java.nio.file.AccessDeniedException;

@Log4j2
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateBookException ex)
    {
        log.error("handleDuplicateException ", ex);

        ErrorResponse errorBody = new ErrorResponse();
        errorBody.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorBody.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        log.error("MethodArgumentNotValidException ", ex);

        return new ResponseEntity<>(
                new ErrorResponse(
                        ex.getMessage()
                        , ex.getBindingResult().getFieldError().getDefaultMessage()
                        , HttpStatus.BAD_REQUEST.value())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GlobalApiException.class)
    public ResponseEntity<ErrorResponse> handleGlobalApiException(GlobalApiException ex)
    {
        log.error("handleGlobalApiException ", ex);
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), ex.getContent(), ex.getCode().value())
                , ex.getCode()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleGlobalApiException(AccessDeniedException ex)
    {
        log.error("AccessDeniedException ", ex);
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value())
                , HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handException(Exception ex) {
        log.error("Exception.class ERROR" + ex.getMessage() + ex.getLocalizedMessage());
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value())
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
