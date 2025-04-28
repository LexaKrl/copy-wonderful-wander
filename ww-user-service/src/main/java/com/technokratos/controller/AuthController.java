package com.technokratos.controller;

import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.UserLoginResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.service.auth.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthUserService userService;

    @PostMapping("/register")
    public UserLoginResponse register(@RequestBody UserRegistrationRequest userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userDto) {
        return userService.verify(userDto);
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.getAll();
    }
}
