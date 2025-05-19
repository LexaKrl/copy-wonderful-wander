package com.technokratos.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class QueryParameter {
    public static final class Pageable {
        public static final String PAGE = "page";
        public static final String SIZE = "size";
    }
}