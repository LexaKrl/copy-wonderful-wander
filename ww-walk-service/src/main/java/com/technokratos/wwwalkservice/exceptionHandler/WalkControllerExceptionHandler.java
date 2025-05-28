package com.technokratos.wwwalkservice.exceptionHandler;

import com.mongodb.MongoException;
import com.technokratos.wwwalkservice.exception.WalkNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WalkControllerExceptionHandler {

    @ExceptionHandler(WalkNotFoundException.class)
    public ResponseEntity<String> handleWalkNotFoundException(WalkNotFoundException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<String> handleValidationException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({DataAccessException.class, MongoException.class})
    public ResponseEntity<String> handleDatabaseExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Database service unavailable with message: %s".formatted(ex.getMessage()));
    }

  /*  @ExceptionHandler(WalkSaveRepositoryException.class)
    public ResponseEntity<String> handleWalkSaveRepositoryException(WalkSaveRepositoryException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error with message: %s".formatted(ex.getMessage()));
    }
}
