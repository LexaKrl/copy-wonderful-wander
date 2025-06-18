package com.technokratos.service.impl;

import com.technokratos.entity.UserData;
import com.technokratos.exception.UserDataNotFound;
import com.technokratos.repository.UserDataRepository;
import com.technokratos.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseUserDataService implements UserDataService {

    private final UserDataRepository userDataRepository;

    @Override
    public UserData getUserDataByUserId(UUID userId) {
        return userDataRepository.findByUserId(userId).orElseThrow(() -> new UserDataNotFound(userId));
    }

    @Override
    public void saveUserData(UserData userData) {
        userDataRepository.save(userData);
    }

    @Override
    public void updateUserData(UserData userData) {
        userDataRepository.save(userData);
    }

    @Override
    public void deleteUserData(UUID userId) {
        userDataRepository.deleteUserDataByUserId(userId);
    }

    @Override
    public void updateFcmToken(UUID userId, String fcmToken) {
        UserData userData = getUserDataByUserId(userId);
        userData.setFcmToken(fcmToken);
        userDataRepository.save(userData);
    }

    @Override
    public String getFcmToken(UUID userId) {
        return userDataRepository.findFcmTokenByUserId(userId).orElse(null);
    }
}
