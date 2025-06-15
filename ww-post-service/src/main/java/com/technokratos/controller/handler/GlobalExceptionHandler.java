package com.technokratos.controller.handler;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.exception.BadRequestServiceException;
import com.technokratos.exception.ServiceException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений для REST API.
 */
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Перехватывает и обрабатывает исключения уровня сервиса ({@link ServiceException})
     * Формирует ответ с заданным HTTP-статусом и сообщением ошибки.
     *
     * @param exception выброшенное {@link ServiceException}
     * @return ResponseEntity с объектом {@link BaseExceptionMessage}, содержащим информацию об ошибке
     */
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<BaseExceptionMessage> handleServiceException(ServiceException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(BaseExceptionMessage.builder()
                        .status(exception.getHttpStatus().value())
                        .error(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build()
                );
    }

    /**
     * Перехватывает и обрабатывает исключение BadRequest уровня сервиса ({@link BadRequestServiceException}).
     * На случай того, если ошибка 400 выбрасывается не на уровне валидаторов, а в бизнес логике
     *
     * @param exception выброшенное {@link BadRequestServiceException}
     * @return ResponseEntity с объектом {@link ValidationExceptionMessage}, содержащим информацию об ошибке
     */
    @ExceptionHandler(BadRequestServiceException.class)
    public final ResponseEntity<ValidationExceptionMessage> handleBadRequestServiceException(BadRequestServiceException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ValidationExceptionMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build());
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
                .message(ValidationExceptionMessage.class.getSimpleName())
                .violations(violations)
                .build();
    }

    /**
     * Перехватывает ошибки десериализации JSON в Java-объект, например, если тело запроса некорректно.
     * Обрабатывает исключение {@link HttpMessageNotReadableException}.
     *
     * @param exception исключение, связанное с невозможностью прочитать входящий запрос
     * @return объект {@link ValidationExceptionMessage} с информацией об ошибке
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ValidationExceptionMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ValidationExceptionMessage.builder()
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
                .message(ValidationExceptionMessage.class.getSimpleName())
                .violations(violations)
                .build();
    }

    /**
     * Перехватывает ошибки конвертации значений параметров запроса (например, {@link PathVariable},
     * {@link RequestParam}) в указанный тип. Обрабатывает исключение
     * {@link MethodArgumentTypeMismatchException}.
     *
     * @param exception исключение, вызванное несоответствием типа аргумента
     * @return объект {@link ValidationExceptionMessage} с описанием ошибки
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ValidationExceptionMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return ValidationExceptionMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();
    }
}