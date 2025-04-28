package com.technokratos.service.auth;

import com.technokratos.dto.request.UserForJwtTokenRequest;
import com.technokratos.dto.request.UserLoginRequest;
import com.technokratos.dto.request.UserRegistrationRequest;
import com.technokratos.dto.response.UserLoginResponse;
import com.technokratos.enums.UserRole;
import com.technokratos.mapper.UserMapper;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
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
       UserEntity user = userMapper.userRegistrationRequestToUserEntity(userDto);

       user.setId(UUID.randomUUID());
       user.setRole(UserRole.ROLE_USER);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);

       log.info("user service register");

       UserForJwtTokenRequest userInfo = userMapper.toJwtUserInfo(user);
       return jwtService.generateToken(userInfo);
   }

    public UserLoginResponse verify(UserLoginRequest userDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                                            userDto.username(),
                                                                            userDto.password()));
        if (authentication.isAuthenticated()) {
            UserEntity userEntity = userRepository.findByUsername(userDto.username());
            UserForJwtTokenRequest userInfo = userMapper.toJwtUserInfo(userEntity);
            return jwtService.generateToken(userInfo);
        }
        log.error("User is not authenticated");
        throw new RuntimeException("User is not authenticated");
    }

    public List<UserEntity> getAll() {
       return userRepository.findAll();
    }
}
