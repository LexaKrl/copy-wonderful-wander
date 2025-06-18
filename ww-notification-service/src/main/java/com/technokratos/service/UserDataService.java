package com.technokratos.service;

import com.technokratos.entity.UserData;

import java.util.UUID;

public interface UserDataService {

    UserData getUserDataByUserId(UUID userId);
    void saveUserData(UserData userData);
    void updateUserData(UserData userData);
    void deleteUserData(UUID userId);
    void updateFcmToken(UUID userId, String fcmToken);
    String getFcmToken(UUID userId);
}
