package com.technokratos.service;

import com.technokratos.exception.PhotoUploadException;
import com.technokratos.util.MinioConstant;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final MinioClient minioClient;

    public void saveAvatar(InputStream inputStream, String contentType) {
        final UUID avatarName = UUID.randomUUID();
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MinioConstant.BucketName.AVATARS)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .object(avatarName.toString())
                            .build());
        } catch (Exception e) {
            log.warn("Photo processing error", e);
            throw new PhotoUploadException(e.getMessage());
        }
    }
}
