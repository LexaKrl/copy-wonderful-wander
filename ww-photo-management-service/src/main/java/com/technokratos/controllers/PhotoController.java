package com.technokratos.controllers;

import com.technokratos.api.PhotoApi;
import com.technokratos.dto.PhotoOfWalkUploadRequest;
import com.technokratos.dto.PhotoUploadRequest;
import com.technokratos.dto.response.photo.PhotoFilenameResponse;
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
    public void uploadAvatar(UUID ownerId, MultipartFile avatar) {
        try {
            photoService.saveAvatar(
                    PhotoUploadRequest.builder()
                            .ownerId(ownerId)
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
    public PhotoFilenameResponse uploadPhotoOfPost(UUID ownerId, MultipartFile photo) {
        try {
            return photoService.savePhotoOfPost(
                    PhotoUploadRequest.builder()
                            .ownerId(ownerId)
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
    public void uploadPhotoOfWalk(UUID ownerId, UUID walkId, MultipartFile photo) {
        try {
            photoService.savePhotoOfWalk(
                    PhotoOfWalkUploadRequest.builder()
                            .walkId(walkId)
                            .photoUploadRequest(
                                    PhotoUploadRequest.builder()
                                            .ownerId(ownerId)
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
