package com.technokratos.wwwalkservice.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class WalkRecordDataConflictException extends WalkRecordDataServiceException {
    public WalkRecordDataConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
