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

@Log4j2
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateBookException ex)
    {
        log.error("GlobalExceptionHandler.handleDuplicateException " , ex);

        ErrorResponse errorBody = new ErrorResponse();
        errorBody.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorBody.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        log.error("GlobalExceptionHandler.MethodArgumentNotValidException ", ex);

        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors())
        {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), builder.toString(), HttpStatus.BAD_REQUEST.value())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GlobalApiExcpetion.class)
    public ResponseEntity<ErrorResponse> handleGlobalApiException(GlobalApiExcpetion ex)
    {
        log.error("GlobalExceptionHandler.handleGlobalApiException ", ex);
        return new ResponseEntity<>
                (
                    new ErrorResponse(ex.getMessage(), "ApiException", HttpStatus.INTERNAL_SERVER_ERROR.value())
                    , HttpStatus.INTERNAL_SERVER_ERROR
                );
    }
}
