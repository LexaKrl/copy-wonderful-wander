package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkApi;
import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalkController implements WalkApi {

    private final WalkService walkService;

    @Override
    public Page<WalkResponse> getWalks(Pageable pageable) {
        return walkService.findAll(pageable);
    }

    @Override
    public WalkResponse getWalk(UUID walkId) {
        return walkService.findById(walkId);
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
}
