package com.technokratos.service;

import com.technokratos.dto.PhotoOfWalkUploadRequest;
import com.technokratos.dto.PhotoUploadRequest;
import com.technokratos.producer.PhotoSavedProducer;
import com.technokratos.repository.PhotoRepository;
import com.technokratos.util.FileHelper;
import com.technokratos.util.mapper.FileMetadataMapper;
import com.technokratos.util.s3.FileNameBuilder;
import com.technokratos.util.s3.MinioConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final FileStorageService fileStorageService;
    private final PhotoRepository photoRepository;
    private final PhotoSavedProducer photoSavedProducer;
    private final FileMetadataMapper fileMetadataMapper;

    public void saveAvatar(PhotoUploadRequest photoUploadRequest) {
        String avatarFilename = save(photoUploadRequest, MinioConstant.FileType.AVATARS);

        photoSavedProducer.sendAvatarSavedEvent(photoUploadRequest.ownerId(), avatarFilename);
    }

    public String savePhotoOfPost(PhotoUploadRequest photoUploadRequest) {
        return save(photoUploadRequest, MinioConstant.FileType.POSTS);
    }

    public void savePhotoOfWalk(PhotoOfWalkUploadRequest photoOfWalkUploadRequest) {
        String photoOfWalkFilename = save(photoOfWalkUploadRequest.photoUploadRequest(), MinioConstant.FileType.WALKS);

        photoSavedProducer.sendPhotoOfWalkSavedEvent(
                photoOfWalkUploadRequest.walkId(),
                photoOfWalkUploadRequest.photoUploadRequest().ownerId(),
                photoOfWalkFilename);
    }

    public String save(PhotoUploadRequest photoUploadRequest, String fileType) {
        String filename = fileStorageService.savePhotoToS3(photoUploadRequest, fileType);

        saveFileMetadata(photoUploadRequest, fileType);

        return filename;
    }

    public void saveFileMetadata(PhotoUploadRequest photoUploadRequest, String fileType) {
        System.out.println(fileType);
        photoRepository.save(fileMetadataMapper.toEntity(
                photoUploadRequest,
                FileNameBuilder.getFileExtension(photoUploadRequest.filename()),
                fileType,
                FileHelper.convertBytesToMegabytes(photoUploadRequest.size()),
                LocalDateTime.now()));
    }
}
