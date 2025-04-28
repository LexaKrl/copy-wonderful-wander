package com.technokratos.api;

import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.UserProfileResponse;
import com.technokratos.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/profile")
public interface ProfileApi {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    UserProfileResponse getProfile();

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    UserResponse updateUser(UserRequest userRequest);
}
