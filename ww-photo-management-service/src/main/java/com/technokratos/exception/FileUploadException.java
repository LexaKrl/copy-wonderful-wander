package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class FileUploadException extends ServiceException {
    public FileUploadException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
