package com.technokratos.wwwalkservice.exceptionHandler;

import com.technokratos.wwwalkservice.exception.WalkNotFoundException;
import com.technokratos.wwwalkservice.exception.WalkSaveRepositoryException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WalkControllerExceptionHandler {

    @ExceptionHandler(WalkNotFoundException.class)
    public ResponseEntity<String> handleWalkNotFoundException(WalkNotFoundException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(WalkSaveRepositoryException.class)
    public ResponseEntity<String> handleWalkSaveRepositoryException(WalkSaveRepositoryException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}
