package com.technokratos.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> data,
        int total,
        int limit,
        int offset) {
}
