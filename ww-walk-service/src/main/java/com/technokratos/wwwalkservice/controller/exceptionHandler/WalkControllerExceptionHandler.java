package com.technokratos.wwwalkservice.controller.exceptionHandler;

import com.mongodb.MongoException;
import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.wwwalkservice.exception.WalkNotFoundException;
import com.technokratos.wwwalkservice.exception.WalkParticipantOverflowException;
import com.technokratos.wwwalkservice.exception.WalkServiceException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class WalkControllerExceptionHandler {

    /**
     * Перехватывает и обрабатывает исключения уровня сервиса ({@link WalkServiceException}).
     * Формирует ответ с заданным HTTP-статусом и сообщением ошибки.
     *
     * @param exception выброшенное {@link WalkServiceException}
     * @return ResponseEntity с объектом {@link BaseExceptionMessage}, содержащим информацию об ошибке
     */
    @ExceptionHandler(WalkServiceException.class)
    public final ResponseEntity<BaseExceptionMessage> handleServiceException(WalkServiceException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(BaseExceptionMessage.builder()
                        .status(exception.getHttpStatus().value())
                        .error(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build()
                );
    }

    /**
     * Перехватывает ошибки валидации DTO, возникающие при передаче неверных данных в теле запроса.
     * Обрабатывает исключение {@link MethodArgumentNotValidException}.
     * Формирует список нарушений валидации по полям.
     *
     * @param exception исключение, содержащее результаты валидации
     * @return объект {@link ValidationExceptionMessage} с деталями ошибок валидации
     * @see ValidationExceptionMessage.Violation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ValidationExceptionMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<ValidationExceptionMessage.Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> ValidationExceptionMessage.Violation.builder()
                        .fieldName(error.getField())
                        .message(error.getDefaultMessage())
                        .build()
                )
                .collect(Collectors.toList());
        return ValidationExceptionMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getClass().getSimpleName())
                .violations(violations)
                .build();
    }

    /**
     * Перехватывает ошибки десериализации JSON в Java-объект, например, если тело запроса некорректно.
     * Обрабатывает исключение {@link HttpMessageNotReadableException}.
     *
     * @param exception исключение, связанное с невозможностью прочитать входящий запрос
     * @return объект {@link BaseExceptionMessage} с информацией об ошибке
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final BaseExceptionMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return BaseExceptionMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
    }

    /**
     * Перехватывает ошибки валидации параметров, аннотированных с использованием Bean Validation,
     * таких как {@code @NotBlank}, {@code @Min}, и других, в значениях из {@link PathVariable} или {@link RequestParam}.
     * Обрабатывает исключение {@link ConstraintViolationException}.
     *
     * @param exception исключение, содержащее информацию о нарушениях ограничений
     * @return объект {@link ValidationExceptionMessage} с перечнем нарушений
     * @see ValidationExceptionMessage.Violation
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionMessage handleConstraintViolationException(ConstraintViolationException exception) {
        final List<ValidationExceptionMessage.Violation> violations = exception.getConstraintViolations().stream()
                .map(violation -> ValidationExceptionMessage.Violation.builder()
                        .fieldName(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build()
                )
                .collect(Collectors.toList());
        return ValidationExceptionMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getClass().getSimpleName())
                .violations(violations)
                .build();
    }

    /**
     * Перехватывает ошибки конвертации значений параметров запроса (например, {@link PathVariable},
     * {@link RequestParam}) в указанный тип. Обрабатывает исключение
     * {@link MethodArgumentTypeMismatchException}.
     *
     * @param exception исключение, вызванное несоответствием типа аргумента
     * @return объект {@link BaseExceptionMessage} с описанием ошибки
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final BaseExceptionMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return BaseExceptionMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
    }
}
