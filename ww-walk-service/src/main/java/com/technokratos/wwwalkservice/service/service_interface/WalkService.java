package com.technokratos.wwwalkservice.service.service_interface;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WalkService {

    Page<WalkResponse> findAll(Pageable pageable);

    WalkResponse findById(UUID id);

    @Transactional
    void deleteById(UUID id);

    @Transactional
    void saveWalk(WalkRequest walkRequest);

    @Transactional
    void updateById(UUID id, WalkRequest walkRequest);

    @Transactional
    void addParticipant(UUID walkId, UUID participantsIds);

    @Transactional
    void removeParticipant(UUID walkId, UUID participantsIds);

    boolean isOwner(UUID walkId, UUID supposedOwnerId);

    boolean isParticipant(UUID walkId, UUID supposedParticipantId);
}
