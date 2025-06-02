package ru.itis.danyook.exception;

import org.springframework.http.HttpStatus;

public class ConflictServiceException extends ServiceException{
    public ConflictServiceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}