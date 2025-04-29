package com.technokratos.wwwalkservice.repository;

import com.technokratos.wwwalkservice.entity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface WalkRepository extends JpaRepository<Walk, UUID> {
    void updateDescriptionById(UUID id, String description);

    void updateNameById(UUID id, String name);

    void updateUserIdById(UUID id, String userId);
}
