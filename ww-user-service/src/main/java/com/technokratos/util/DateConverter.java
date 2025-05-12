package com.technokratos.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateConverter {

    public LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
    }

    public Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
