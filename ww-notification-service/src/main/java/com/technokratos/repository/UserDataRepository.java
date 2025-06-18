package com.technokratos.repository;

import com.technokratos.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    @Query("SELECT u FROM UserData u WHERE u.userId = :userId")
    Optional<UserData> findByUserId(@Param("userId") UUID userId);

    void deleteUserDataByUserId(UUID userId);

    @Query("SELECT u.fcmToken FROM UserData u WHERE u.userId = :userId")
    Optional<String> findFcmTokenByUserId(@Param("userId") UUID userId);
}
