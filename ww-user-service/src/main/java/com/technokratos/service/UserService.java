package com.technokratos.service;

import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

   private final UserRepository userRepository;
   private final AuthenticationManager authenticationManager;
   private final BCryptPasswordEncoder passwordEncoder;


   public UserEntity register(UserEntity user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
       log.info("user service register");
       return user;
   }

    public String verify(UserEntity user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                                        user.getUsername(),
                                                                        user.getPassword()));
        if (authentication.isAuthenticated()) {
            return "success";
        }

        return "fail";
    }
}
