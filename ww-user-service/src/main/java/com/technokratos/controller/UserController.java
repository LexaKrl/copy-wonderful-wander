package com.technokratos.controller;

import com.technokratos.api.UserApi;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public UserResponse getUserById(UUID userId) {
        return userService.getUserById(userId);
    }
}
