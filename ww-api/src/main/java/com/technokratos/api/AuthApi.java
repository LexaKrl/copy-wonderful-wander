package com.technokratos.api;


import com.technokratos.dto.request.security.PasswordChangeRequest;
import com.technokratos.dto.request.security.RefreshTokenRequest;
import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Auth", description = "The auth API")
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthApi {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    AuthResponse register(@RequestBody UserRegistrationRequest userDto);


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    AuthResponse login(@RequestBody UserLoginRequest userDto);

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest);

    @PostMapping("/change-pass")
    void changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
