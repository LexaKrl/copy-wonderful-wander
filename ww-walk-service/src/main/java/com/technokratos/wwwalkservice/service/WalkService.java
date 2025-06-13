package com.technokratos.wwwalkservice.service;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface WalkService {

    Page<WalkResponse> findAll(Pageable pageable);

    Page<WalkResponse> findAllForUser(Pageable pageable);

    WalkResponse findById(UUID id);

    @Transactional
    void deleteById(UUID id);

    @Transactional
    void saveWalk(WalkRequest walkRequest);

    @Transactional
    void updateById(UUID id, WalkRequest walkRequest);

    @Transactional
    void addParticipant(UUID walkId, UUID participantId);

    @Transactional
    void removeParticipant(UUID walkId, UUID participantId);

    boolean isOwner(UUID walkId);

    boolean isParticipant(UUID walkId);

    Page<WalkResponse> findAllWhereUserParticipant(Pageable pageable);

    Page<WalkResponse> findAllUserSubscribedOn(Pageable pageable);
}
