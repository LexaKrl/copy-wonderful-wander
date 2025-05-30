package com.technokratos.api;

import com.technokratos.validation.image.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RequestMapping(value = "/api/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public interface PhotoApi {

    @PostMapping(value = "/avatar")
    @ResponseStatus(HttpStatus.CREATED)
    void uploadAvatar(@Validated @Image @RequestPart("avatar") MultipartFile avatar);

    @PostMapping(value = "/post")
    @ResponseStatus(HttpStatus.CREATED)
    String uploadPhotoOfPost(@Validated @Image @RequestPart("photo") MultipartFile photo);

    @PostMapping(value = "walk")
    @ResponseStatus(HttpStatus.CREATED)
    void uploadPhotoOfWalk(@RequestParam UUID walkId, @Validated @Image @RequestPart("photo") MultipartFile photo);
}
