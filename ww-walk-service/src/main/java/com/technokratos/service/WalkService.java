package com.technokratos.service;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WalkService {

    Page<WalkResponse> findAllVisible(UUID userId, List<UUID> subscribers, Pageable pageable);

    Page<WalkResponse> findAllVisibleForUser(UUID userId, UUID requesterId, List<UUID> subscribers, Pageable pageable);

    WalkResponse findById(UUID requesterId, List<UUID> subscribers, UUID walkId);

    @Transactional
    void deleteById(UUID currentUserId, UUID id);

    @Transactional
    void saveWalk(UUID currentUserId, WalkRequest walkRequest);

    @Transactional
    void updateById(UUID currentUserId, UUID walkId, WalkRequest walkRequest);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addParticipant(UUID currentUserId, UUID walkId, UUID participantId);

    @Transactional
    void removeParticipant(UUID currentUserId, UUID walkId, UUID participantId);

    boolean isOwner(UUID currentUserId, UUID walkId);

    boolean isParticipant(UUID currentUserId, UUID walkId);

    Page<WalkResponse> findAllVisibleWhereUserParticipant(UUID requesterId, List<UUID> subscribers, Pageable pageable);

    Page<WalkResponse> findAllVisibleUserSubscribedOn(UUID requesterId, List<UUID> subscribers, Pageable pageable);

    @Transactional
    void acceptInvite(UUID walkId, UUID participantId, UUID acceptationToken);

}
