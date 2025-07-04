package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class WalkForbiddenException extends WalkServiceException {
    public WalkForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
