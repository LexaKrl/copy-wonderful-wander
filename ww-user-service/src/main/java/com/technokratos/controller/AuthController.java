package com.technokratos.controller;

import com.technokratos.model.UserEntity;
import com.technokratos.service.auth.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.mapstruct.control.MappingControl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthUserService userService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user) {
        userService.register(user);
        log.info("auth controller register");
        return user;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity user) {
        return userService.verify(user);
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return userService.getAll();
    }


}
