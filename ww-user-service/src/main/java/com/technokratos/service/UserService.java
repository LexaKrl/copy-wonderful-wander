package com.technokratos.service;

import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

   private final UserRepository userRepository;

   private BCryptPasswordEncoder passwordEncoder;


   public UserEntity register(UserEntity user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
       return user;
   }

}
