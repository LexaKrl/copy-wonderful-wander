package com.technokratos.api;

import com.technokratos.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequestMapping("/api/users")
public interface UserApi {
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse getUserById(@PathVariable UUID userId);
}