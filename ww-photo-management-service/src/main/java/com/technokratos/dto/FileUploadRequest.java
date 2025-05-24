package com.technokratos.dto;

import java.io.InputStream;
import java.util.UUID;

public record FileUploadRequest(
        UUID userId,
        InputStream inputStream,
        String filename,
        String contentType,
        long size) {
}
