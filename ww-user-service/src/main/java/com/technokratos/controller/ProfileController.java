package com.technokratos.controller;

import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.model.UserEntity;
import com.technokratos.model.UserPrincipal;
import com.technokratos.security.BaseUserContextHolder;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Slf4j
public class ProfileController {

    private final BaseUserContextHolder baseUserContextHolder;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public UserResponse getProfile() {
        UserEntity user = baseUserContextHolder.getUserFromSecurityContext();
        log.info("User: {}", user);
        return userMapper.toResponse(user);
    }
}