package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class WalkConflictServiceException extends WalkServiceException {

    public WalkConflictServiceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
