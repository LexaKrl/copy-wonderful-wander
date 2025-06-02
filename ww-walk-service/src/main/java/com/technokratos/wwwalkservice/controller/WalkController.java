package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkApi;
import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.service.service_interface.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalkController implements WalkApi {

    private final WalkService walkService;

    @Override
    public ResponseEntity<Page<WalkResponse>> getWalks(Pageable pageable) {
        return ResponseEntity.ok(walkService.findAll(pageable));
    }

    @Override
    public ResponseEntity<Void> createWalk(WalkRequest walkRequest) {
        walkService.saveWalk(walkRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<WalkResponse> getWalk(UUID walkId) {
        return ResponseEntity.ok(walkService.findById(walkId));
    }

    @Override
    /*
    @PreAuthorize("baseWalkService.isOwner(walkId, ownerID)")
    */
    public ResponseEntity<Void> deleteWalk(UUID walkId) {
        walkService.deleteById(walkId);
        return ResponseEntity.noContent().build();
    }

    @Override
    /*
    @PreAuthorize("baseWalkService.isOwner(walkId, ownerID)")
    */
    public ResponseEntity<Void> updateWalk(WalkRequest walkRequest, UUID walkId) {
        walkService.updateById(walkId, walkRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    /*
    @PreAuthorize("baseWalkService.isOwner(walkId, ownerId)")
    */
    public ResponseEntity<Void> addParticipant(UUID participantId, UUID walkId) {
        walkService.addParticipant(walkId, participantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    /*
    @PreAuthorize("baseWalkService.isOwner(walkId, ownerID)")
    */
    public ResponseEntity<Void> removeParticipant(UUID participantId, UUID walkId) {
        walkService.removeParticipant(walkId, participantId);
        return ResponseEntity.noContent().build();
    }
}
