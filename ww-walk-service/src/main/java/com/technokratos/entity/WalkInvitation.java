package com.technokratos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "walk_invitations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalkInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_id", nullable = false)
    private Walk walk;

    @Column(name = "participant_id", nullable = false)
    private UUID participantId;

    @Column(name = "token", nullable = false, unique = true)
    private UUID token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "accepted", nullable = false)
    @Builder.Default
    private boolean accepted = false;
}
