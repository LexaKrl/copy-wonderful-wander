package com.technokratos.controller.handler;

import com.technokratos.controller.handler.exception.BaseExceptionMessage;
import com.technokratos.controller.handler.exception.ValidationExceptionMessage;
import com.technokratos.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<BaseExceptionMessage> handleServiceException(ServiceException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(BaseExceptionMessage.builder()
                        .error(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionMessage handleValidationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        System.out.println(req.getRequestURL());
        return ValidationExceptionMessage.builder()
                .error("Validation Exception")
                .fieldErrors(errors)
                .build();
    }
}
