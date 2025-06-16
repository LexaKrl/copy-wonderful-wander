package com.technokratos.repository;

import com.technokratos.entity.WalkInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalkInvitationRepository extends JpaRepository<WalkInvitation, UUID> {
    void deleteByExpiresAtBefore(LocalDateTime expiresAt);
    void deleteByAcceptedTrue();
    Optional<WalkInvitation> findByToken(UUID token);
}
