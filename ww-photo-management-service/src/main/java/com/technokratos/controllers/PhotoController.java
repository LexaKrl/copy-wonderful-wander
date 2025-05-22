package com.technokratos.controllers;

import com.technokratos.api.PhotoApi;
import com.technokratos.exception.EmptyPhotoUploadException;
import com.technokratos.exception.PhotoUploadException;
import com.technokratos.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotoApi {
    private final PhotoService photoService;

    @Override
    public void uploadAvatar(MultipartFile avatar) {
        if (!avatar.isEmpty()) {
            try {
                photoService.saveAvatar(avatar.getInputStream(), avatar.getContentType());
            } catch (IOException e) {
                throw new PhotoUploadException(e.getMessage());
            }
        } else {
            throw new EmptyPhotoUploadException();
        }
    }
}
