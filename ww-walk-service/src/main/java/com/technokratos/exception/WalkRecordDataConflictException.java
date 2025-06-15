package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class WalkRecordDataConflictException extends WalkRecordDataServiceException {
    public WalkRecordDataConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
