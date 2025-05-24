package com.technokratos.service;

import com.technokratos.dto.FileUploadRequest;
import com.technokratos.entity.FileMetadata;
import com.technokratos.exception.PhotoUploadException;
import com.technokratos.repository.PhotoRepository;
import com.technokratos.util.s3.FileNameBuilder;
import com.technokratos.util.s3.MinioConstant;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final MinioClient minioClient;
    private final PhotoRepository photoRepository;

    public void saveAvatar(FileUploadRequest fileUploadRequest) {
        save(fileUploadRequest, MinioConstant.BucketName.PHOTOS, MinioConstant.FileType.AVATARS);
    }

    public UUID savePost(FileUploadRequest fileUploadRequest) {
        return save(fileUploadRequest, MinioConstant.BucketName.PHOTOS, MinioConstant.FileType.POSTS);
    }

    public UUID saveWalk(FileUploadRequest fileUploadRequest) {
        return save(fileUploadRequest, MinioConstant.BucketName.PHOTOS, MinioConstant.FileType.WALKS);
    }


    private UUID save(FileUploadRequest fileUploadRequest, String bucketName, String fileType) {
        UUID fileId = UUID.randomUUID();
        String fileName = FileNameBuilder.buildFileName(fileId, fileUploadRequest.filename(), fileType);

        try (InputStream inputStream = fileUploadRequest.inputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .stream(
                                    inputStream,
                                    fileUploadRequest.size(),
                                    -1)
                            .contentType(fileUploadRequest.contentType())
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            log.warn("Image processing error", e);
            throw new PhotoUploadException(e.getMessage());
        }

        return photoRepository.save(FileMetadata.builder()
                .fileId(fileId)
                .ownerId(fileUploadRequest.userId())
                .filename(fileUploadRequest.filename())
                .extension(FileNameBuilder.getFileExtension(fileUploadRequest.filename()))
                .size(convertBytesToMegabytes(fileUploadRequest.size()))
                .uploadDateTime(LocalDateTime.now())
                .build());
    }

    private double convertBytesToMegabytes(long size) {
        return Math.round(((double) size / (1024 * 1024)) * 10) / 10.0;
    }
}
