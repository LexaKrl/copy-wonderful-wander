package com.technokratos.service.auth;

import com.technokratos.dto.request.security.UserForJwtTokenRequest;
import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.UserLoginResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.enums.security.UserRole;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import com.technokratos.tables.pojos.Account;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserMapper userMapper;


    public UserLoginResponse register(UserRegistrationRequest userDto) {
        log.info("register data: userdto: {}", userDto);
        UserEntity user = userMapper.userRegistrationRequestToUserEntity(userDto);

        user.setUserId(UUID.randomUUID());
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("register data: user: {}", user);
        userRepository.save(user);

        log.info("user service register");

        UserForJwtTokenRequest userInfo = userMapper.toJwtUserInfo(user);
        log.info("register data: userInfo for jwt: {}", userInfo);
        return jwtService.generateTokens(userInfo);
    }

    public UserLoginResponse verify(UserLoginRequest userDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        userDto.username(),
                        userDto.password()));
        if (authentication.isAuthenticated()) {
            Account account = userRepository.findByUsername(userDto.username()).orElseThrow(() -> new RuntimeException("User not found"));
            UserEntity userEntity = userMapper.accountToUserEntity(account);
            UserForJwtTokenRequest userInfo = userMapper.toJwtUserInfo(userEntity);
            return jwtService.generateTokens(userInfo);
        }
        log.error("User is not authenticated");
        throw new RuntimeException("User is not authenticated");
    }

    public List<UserResponse> getAll() {
        return userMapper.toResponse(userRepository.findAll());
    }
}
