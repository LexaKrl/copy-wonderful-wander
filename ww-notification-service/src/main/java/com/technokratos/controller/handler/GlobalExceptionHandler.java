package com.technokratos.controller.handler;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Перехватывает и обрабатывает исключения уровня сервиса ({@link ServiceException}).
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
