package com.technokratos.controllers;

import com.technokratos.api.PhotoApi;
import com.technokratos.dto.PhotoOfWalkUploadRequest;
import com.technokratos.dto.PhotoUploadRequest;
import com.technokratos.exception.FileUploadException;
import com.technokratos.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotoApi {
    private final PhotoService photoService;

    @Override
    public void uploadAvatar(MultipartFile avatar) {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        try {
            photoService.saveAvatar(
                    PhotoUploadRequest.builder()
                            .ownerId(userId)
                            .photoId(UUID.randomUUID())
                            .inputStream(avatar.getInputStream())
                            .filename(avatar.getOriginalFilename())
                            .size(avatar.getSize())
                            .contentType(avatar.getContentType())
                            .build()
            );
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }

    @Override
    public String uploadPhotoOfPost(MultipartFile photo) {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        try {
            return photoService.savePhotoOfPost(
                    PhotoUploadRequest.builder()
                            .ownerId(userId)
                            .photoId(UUID.randomUUID())
                            .inputStream(photo.getInputStream())
                            .filename(photo.getOriginalFilename())
                            .size(photo.getSize())
                            .contentType(photo.getContentType())
                            .build()
            );
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }

    @Override
    public void uploadPhotoOfWalk(UUID walkId, MultipartFile photo) {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        try {
            photoService.savePhotoOfWalk(
                    PhotoOfWalkUploadRequest.builder()
                            .walkId(walkId)
                            .photoUploadRequest(
                                    PhotoUploadRequest.builder()
                                            .ownerId(userId)
                                            .photoId(UUID.randomUUID())
                                            .inputStream(photo.getInputStream())
                                            .filename(photo.getOriginalFilename())
                                            .size(photo.getSize())
                                            .contentType(photo.getContentType())
                                            .build()
                            )
                            .build()
            );
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }
}
