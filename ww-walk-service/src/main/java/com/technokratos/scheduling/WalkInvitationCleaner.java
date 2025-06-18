package com.technokratos.scheduling;

import com.technokratos.repository.WalkInvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class WalkInvitationCleaner {

    private final WalkInvitationRepository walkInvitationRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanupExpiredInvitations() {
        walkInvitationRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void cleanupExpiredInvitationsAgain() {
        walkInvitationRepository.deleteByAcceptedTrue();
    }
}
