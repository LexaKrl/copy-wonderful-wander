package com.technokratos.controller;

import com.technokratos.api.AuthApi;
import com.technokratos.dto.request.security.RefreshTokenRequest;
import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.AuthResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.service.auth.AuthUserService;
import com.technokratos.service.auth.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthUserService userService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse register(@RequestBody UserRegistrationRequest userDto) {
        return userService.register(userDto);
    }

    @Override
    public AuthResponse login(@RequestBody UserLoginRequest userDto) {
        return userService.verify(userDto);
    }

    @Override
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshAccessToken(refreshTokenRequest.refreshToken());
    }
}
