package com.technokratos.service;

import com.technokratos.dto.PhotoUploadRequest;
import com.technokratos.exception.FileUploadException;
import com.technokratos.util.s3.FileNameBuilder;
import com.technokratos.util.s3.MinioConstant;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final MinioClient minioClient;

    public String savePhotoToS3(PhotoUploadRequest photoUploadRequest, String fileType) {
        String fileName = FileNameBuilder.buildFileName(photoUploadRequest.photoId(), photoUploadRequest.filename(), fileType);

        try (InputStream inputStream = photoUploadRequest.inputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MinioConstant.BucketName.PHOTOS)
                            .stream(
                                    inputStream,
                                    photoUploadRequest.size(),
                                    -1)
                            .contentType(photoUploadRequest.contentType())
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            log.warn("Image processing error", e);
            throw new FileUploadException(e.getMessage());
        }

        return fileName;
    }
}
