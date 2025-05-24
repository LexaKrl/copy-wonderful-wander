package com.technokratos.controllers;

import com.technokratos.api.PhotoApi;
import com.technokratos.dto.FileUploadRequest;
import com.technokratos.exception.PhotoUploadException;
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
                    new FileUploadRequest(
                            userId,
                            avatar.getInputStream(),
                            avatar.getOriginalFilename(),
                            avatar.getContentType(),
                            avatar.getSize()
                    ));
        } catch (IOException e) {
            throw new PhotoUploadException(e.getMessage());
        }
    }
}
