package com.technokratos.service;

import com.technokratos.config.properties.MinioProperties;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private final MinioProperties properties;

    public String getPresignedUrl(String filename) {
        if (filename == null) return null;

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(properties.getBucket())
                            .object(filename)
                            .expiry(properties.getPresignedUrlExpiration(), TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.warn("Error getting presigned URL for file '{}'", filename, e);
            throw new RuntimeException(e);
        }
    }
}

