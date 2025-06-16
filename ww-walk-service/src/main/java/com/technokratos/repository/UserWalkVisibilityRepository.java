package com.technokratos.repository;

import com.technokratos.entity.UserWalkVisibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserWalkVisibilityRepository extends JpaRepository<UserWalkVisibility, UUID> {

    void deleteByUserId(UUID userId);
}
