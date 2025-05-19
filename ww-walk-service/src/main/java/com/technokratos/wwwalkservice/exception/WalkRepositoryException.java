package com.technokratos.wwwalkservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WalkRepositoryException extends RuntimeException {

    private final HttpStatus httpStatus;

    public WalkRepositoryException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
