package com.technokratos.controller;

import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.model.UserEntity;
import com.technokratos.model.UserPrincipal;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserMapper userMapper;

    @GetMapping("/me")
    public UserResponse getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserEntity user = userPrincipal.getUser();
        return userMapper.toResponse(user);
    }
}