package com.technokratos.service;

import com.technokratos.dto.response.UserResponse;
import com.technokratos.repository.UserRepository;
import com.technokratos.tables.records.UserInfoRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserById(UUID userId) {
        UserInfoRecord userFromDb = userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("Not found user with uuid %s".formatted(userId)));
        return new UserResponse(userFromDb.getUsername());
    }
}
