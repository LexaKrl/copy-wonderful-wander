package com.technokratos.service.auth;

import com.technokratos.dto.request.security.PasswordChangeRequest;
import com.technokratos.dto.UserInfoForJwt;
import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.AuthResponse;
import com.technokratos.enums.security.UserRole;
import com.technokratos.exception.PasswordNotMatchException;
import com.technokratos.exception.UserByIdNotFoundException;
import com.technokratos.exception.UserByUsernameNotFoundException;
import com.technokratos.exception.UsernameNotUniqueException;
import com.technokratos.model.UserEntity;
import com.technokratos.model.UserPrincipal;
import com.technokratos.producer.UserEventProducer;
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

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final MyUserDetailsService userDetailsService;
    private final UserEventProducer userEventProducer;

    public AuthResponse register(UserRegistrationRequest userDto) {
        log.info("user service register doing");

        if (!userDto.password().equals(userDto.duplicatePassword())) {
            throw new PasswordNotMatchException("Passwords don't match");
        }

        if (userRepository.findByUsername(userDto.username()).isPresent()) {
            throw new UsernameNotUniqueException(userDto.username());
        }

        UserEntity user = userMapper.userRegistrationRequestToUserEntity(userDto);

        user.setUserId(UUID.randomUUID());
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        Account newUser = userRepository.findByUsername(userDto.username())
                .orElseThrow(() -> new UserByUsernameNotFoundException(userDto.username()));
        userEventProducer.sendUserCreatedEvent(userMapper.toUserCreatedEvent(newUser));

        UserInfoForJwt userInfo = userMapper.toJwtUserInfo(user);
        return jwtProvider.generateTokens(userInfo);
    }

    public AuthResponse verify(UserLoginRequest userDto) {
        log.info("Verify user: {}", userDto.username());
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        userDto.username(),
                        userDto.password()));
        if (authentication.isAuthenticated()) {
            UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(userDto.username());
            UserInfoForJwt userInfo = userMapper.toJwtUserInfo(user);
            return jwtProvider.generateTokens(userInfo);
        }
        throw new RuntimeException("User is not authenticated");
    }

    public void changePassword(UUID userId, PasswordChangeRequest passwordChangeRequest) {
        String oldPassword = passwordChangeRequest.oldPassword();
        String newPassword = passwordChangeRequest.newPassword();
        String newDuplicatePassword = passwordChangeRequest.newDuplicatePassword();
        Account account = userRepository.findById(userId)
                .orElseThrow(() -> new UserByIdNotFoundException(userId));

        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new PasswordNotMatchException("The old password doesn't match");
        }

        if (!newPassword.equals(newDuplicatePassword)) {
            throw new PasswordNotMatchException("The duplicated password doesn't match");
        }

        userRepository.changePassword(userId, passwordEncoder.encode(passwordChangeRequest.newPassword()));
    }
}
