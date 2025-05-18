package com.technokratos.controller;

import com.technokratos.api.AuthApi;
import com.technokratos.dto.request.security.PasswordChangeRequest;
import com.technokratos.dto.request.security.RefreshTokenRequest;
import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.AuthResponse;
import com.technokratos.model.UserPrincipal;
import com.technokratos.security.BaseUserContextHolder;
import com.technokratos.service.auth.AuthUserService;
import com.technokratos.service.auth.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final AuthUserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthUserService authUserService;
    private final BaseUserContextHolder baseUserContextHolder;

    @Override
    public AuthResponse register(UserRegistrationRequest userDto) {
        return userService.register(userDto);
    }

    @Override
    public AuthResponse login(UserLoginRequest userDto) {
        return userService.verify(userDto);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshAccessToken(refreshTokenRequest.refreshToken());
    }

    @GetMapping("/me")
    public UserPrincipal getProfile() {
        UserPrincipal user = baseUserContextHolder.getUserFromSecurityContext();
        log.info("UserPrincipal: {}", user);
        return user;
    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        UUID userId = baseUserContextHolder.getUserFromSecurityContext().getUserId();
        authUserService.changePassword(userId, passwordChangeRequest);
    }
}
