package com.technokratos.wwwalkservice.repository;

import com.technokratos.wwwalkservice.entity.UserWalkVisibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserWalkVisibilityRepository extends JpaRepository<UserWalkVisibility, UUID> {

    void deleteByUserId(UUID userId);
}
