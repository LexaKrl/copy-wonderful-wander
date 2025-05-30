package com.technokratos.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public final class FileHelper {
    public static Double convertBytesToMegabytes(long size) {
        return new BigDecimal(size)
                .divide(new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
