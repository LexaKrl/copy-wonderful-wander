package com.technokratos.controller;

import com.technokratos.api.WalkRecordingApi;
import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.service.RecordLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalkRecordingController implements WalkRecordingApi {

    private final RecordLocationService locationService;

    @Override
    public SseEmitter subscribe(UUID currentUserId, UUID walkId) {
        return locationService.subscribe(currentUserId, walkId);
    }

    @Override
    public void finishWalk(UUID currentUserId, UUID walkId) {
        locationService.finishWalk(currentUserId, walkId);
    }

    @Override
    public void streamData(UUID currentUserId, UUID walkId, WalkDataRequest walkDataRequest) {
        locationService.streamData(currentUserId, walkId, walkDataRequest);
    }
}
