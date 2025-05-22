package com.technokratos.api;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@MultipartConfig
@RequestMapping("/api/photos")
public interface PhotoApi {

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void uploadAvatar(@RequestPart("avatar") MultipartFile multipartFile);
}
