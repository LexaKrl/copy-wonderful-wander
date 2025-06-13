package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkRecordingApi;
import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.wwwalkservice.service.RecordLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalkRecordingController implements WalkRecordingApi {

    private final RecordLocationService locationService;

    /* TODO add requestHeader to all of methods in this controller */

    @Override
    public SseEmitter subscribe(UUID walkId) {
        return locationService.subscribe(walkId);
    }

    @Override
    public void finishWalk(UUID walkId) {
        locationService.finishWalk(walkId);
    }

    @Override
    public void streamData(UUID walkId, WalkDataRequest walkDataRequest) {
        locationService.streamData(walkId, walkDataRequest);
    }
}
