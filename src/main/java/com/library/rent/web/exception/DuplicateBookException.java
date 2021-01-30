package com.library.rent.web.exception;

public class DuplicateBookException extends RuntimeException {

    public DuplicateBookException() {
    }

    public DuplicateBookException(String message) {
        super(message);
    }

    public DuplicateBookException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DuplicateBookException(Throwable throwable) {
        super(throwable);
    }
}
