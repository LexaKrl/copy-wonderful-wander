package com.technokratos.wwwalkservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WalkServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public WalkServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
