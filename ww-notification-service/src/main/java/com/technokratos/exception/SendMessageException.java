package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class SendMessageException extends ServiceException {
    public SendMessageException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
