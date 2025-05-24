package com.technokratos.listener;

import com.technokratos.util.s3.MinioConstant;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioBucketInitializer {
    private static final List<String> BUCKETS = List.of(
            MinioConstant.BucketName.PHOTOS
    );
    private final MinioClient minioClient;

    @EventListener(ApplicationReadyEvent.class)
    public void createBucketsIfNotExists() {
        for (String bucket : BUCKETS) {
            checkAndCreateBucket(bucket);
        }
    }

    private void checkAndCreateBucket(String bucketName) {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                log.info("Bucket '{}' has been successfully created", bucketName);
            } else {
                log.info("Bucket '{}' already exists", bucketName);
            }
        } catch (Exception e) {
            log.error("Failed to verify or create bucket '{}'", bucketName, e);
        }
    }
}
