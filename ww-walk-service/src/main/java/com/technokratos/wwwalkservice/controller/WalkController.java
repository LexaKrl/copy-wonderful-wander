package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkApi;
import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalkController implements WalkApi {

    private final WalkService walkService;

    /* TODO add requestHeader to all of methods in this controller */

    @Override
    public Page<WalkResponse> getWalks(UUID requesterId, Pageable pageable) {
        /* TODO Get list of users */
        List<UUID> subscribers = List.of();
        return walkService.findAllVisible(requesterId, subscribers, pageable);
    }

    @Override
    public Page<WalkResponse> getWalksForUser(UUID userId, UUID requesterId, Pageable pageable) {
        /* TODO Get list of users */
        List<UUID> subscribers = List.of();
        return walkService.findAllVisibleForUser(requesterId, userId, subscribers, pageable);
    }

    @Override
    public Page<WalkResponse> getWalksWhereUserParticipant(UUID requesterId, Pageable pageable) {
        /* TODO Get list of users */
        List<UUID> subscribers = List.of();
        return walkService.findAllVisibleWhereUserParticipant(requesterId, subscribers, pageable);
    }

    @Override
    public Page<WalkResponse> getWalksUserSubscribedOn(UUID requesterId, Pageable pageable) {
        /* TODO Get list of users */
        List<UUID> subscribers = List.of();
        return walkService.findAllVisibleUserSubscribedOn(requesterId, subscribers, pageable);
    }

    @Override
    public WalkResponse getWalk(UUID requesterId, UUID walkId) {
        /* TODO Get list of users */
        List<UUID> subscribers = List.of();
        return walkService.findById(requesterId, subscribers, walkId);
    }

    @Override
    public void createWalk(WalkRequest walkRequest) {
        walkService.saveWalk(walkRequest);
    }

    @Override
    public void deleteWalk(UUID walkId) {
        walkService.deleteById(walkId);
    }

    @Override
    public void updateWalk(WalkRequest walkRequest, UUID walkId) {
        walkService.updateById(walkId, walkRequest);
    }

    @Override
    public void addParticipant(UUID participantId, UUID walkId) {
        walkService.addParticipant(walkId, participantId);
    }

    @Override
    public void removeParticipant(UUID participantId, UUID walkId) {
        walkService.removeParticipant(walkId, participantId);
    }

    @Override
    public void acceptInvite(UUID acceptationToken, UUID walkId, UUID participantId) {
        walkService.acceptInvite(walkId, participantId, acceptationToken);
    }
}
