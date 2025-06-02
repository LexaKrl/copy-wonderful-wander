package ru.itis.danyook.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenServiceException extends ServiceException{
    public ForbiddenServiceException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
