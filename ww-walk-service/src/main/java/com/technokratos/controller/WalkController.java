package com.technokratos.controller;

import com.technokratos.api.WalkApi;
import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.service.WalkService;
import com.technokratos.utils.RestTemplateUtils;
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
    private final RestTemplateUtils restTemplateUtils;

    @Override
    public Page<WalkResponse> getWalks(UUID requesterId, Pageable pageable) {
        List<UUID> subscribers = restTemplateUtils.getFollowingByUserId(requesterId);
        return walkService.findAllVisible(requesterId, subscribers, pageable);
    }

    @Override
    public Page<WalkResponse> getWalksForUser(UUID userId, UUID requesterId, Pageable pageable) {
        List<UUID> subscribers = restTemplateUtils.getFollowingByUserId(requesterId);
        return walkService.findAllVisibleForUser(requesterId, userId, subscribers, pageable);
    }

    @Override
    public Page<WalkResponse> getWalksWhereUserParticipant(UUID requesterId, Pageable pageable) {
        List<UUID> subscribers = restTemplateUtils.getFollowingByUserId(requesterId);
        return walkService.findAllVisibleWhereUserParticipant(requesterId, subscribers, pageable);
    }

    @Override
    public Page<WalkResponse> getWalksUserSubscribedOn(UUID requesterId, Pageable pageable) {
        List<UUID> subscribers = restTemplateUtils.getFollowingByUserId(requesterId);
        return walkService.findAllVisibleUserSubscribedOn(requesterId, subscribers, pageable);
    }

    @Override
    public WalkResponse getWalk(UUID requesterId, UUID walkId) {
        List<UUID> subscribers = restTemplateUtils.getFollowingByUserId(requesterId);
        return walkService.findById(requesterId, subscribers, walkId);
    }

    @Override
    public void createWalk(UUID currentUserId, WalkRequest walkRequest) {
        walkService.saveWalk(currentUserId, walkRequest);
    }

    @Override
    public void deleteWalk(UUID currentUserId, UUID walkId) {
        walkService.deleteById(currentUserId, walkId);
    }

    @Override
    public void updateWalk(UUID currentUserId, WalkRequest walkRequest, UUID walkId) {
        walkService.updateById(currentUserId, walkId, walkRequest);
    }

    @Override
    public void addParticipant(UUID currentUserId, UUID participantId, UUID walkId) {
        walkService.addParticipant(currentUserId, walkId, participantId);
    }

    @Override
    public void removeParticipant(UUID currentUserId, UUID participantId, UUID walkId) {
        walkService.removeParticipant(currentUserId, walkId, participantId);
    }

    @Override
    public void acceptInvite(UUID acceptationToken, UUID walkId, UUID participantId) {
        walkService.acceptInvite(walkId, participantId, acceptationToken);
    }
}
