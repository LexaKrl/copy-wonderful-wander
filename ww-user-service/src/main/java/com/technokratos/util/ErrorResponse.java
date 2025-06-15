package com.technokratos.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ErrorResponse {
    private ErrorResponse() {}


    public static void sendErrorResponse(HttpServletResponse response,
                                         HttpStatus status,
                                         Throwable exception) throws IOException {


        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        BaseExceptionMessage errorResponse = BaseExceptionMessage.builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .build();


        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
