package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class PhotoUploadException extends ServiceException {
    public PhotoUploadException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
