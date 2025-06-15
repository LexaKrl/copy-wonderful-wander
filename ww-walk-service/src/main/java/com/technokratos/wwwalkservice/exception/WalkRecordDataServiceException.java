package com.technokratos.wwwalkservice.exception;

import org.springframework.http.HttpStatus;

public class WalkRecordDataServiceException extends WalkServiceException {
    public WalkRecordDataServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
