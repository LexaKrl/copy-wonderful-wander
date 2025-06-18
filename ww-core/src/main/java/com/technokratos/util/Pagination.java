package com.technokratos.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Pagination {
    public static int offset(int total, int page, int limit) {
        return total <= 0 ? 0 : (Math.min(page, (total + limit - 1) / limit) - 1) * limit;
    }
}
