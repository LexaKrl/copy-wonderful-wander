package com.technokratos.repository;

import com.technokratos.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    @Query("SELECT UserData u FROM UserData WHERE u.userId = :userId")
    Optional<UserData> findByUserId(UUID userId);

    void deleteUserDataByUserId(UUID userId);
}
