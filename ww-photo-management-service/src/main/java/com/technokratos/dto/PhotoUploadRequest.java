package com.technokratos.dto;

import lombok.Builder;

import java.io.InputStream;
import java.util.UUID;

@Builder
public record PhotoUploadRequest(
        UUID photoId,
        UUID ownerId,
        InputStream inputStream,
        String filename,
        String contentType,
        long size) {
}
