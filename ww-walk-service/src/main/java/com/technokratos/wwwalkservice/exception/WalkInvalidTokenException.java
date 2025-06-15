package com.technokratos.wwwalkservice.exception;

import org.springframework.http.HttpStatus;

public class WalkInvalidTokenException extends WalkServiceException {
    public WalkInvalidTokenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
