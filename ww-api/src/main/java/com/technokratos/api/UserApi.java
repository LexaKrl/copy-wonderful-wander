package com.technokratos.api;

import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/users")
public interface UserApi {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse getUserById(@PathVariable UUID userId);

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse updateUser(@PathVariable UUID userId, @RequestBody UserRequest userRequest);

}

