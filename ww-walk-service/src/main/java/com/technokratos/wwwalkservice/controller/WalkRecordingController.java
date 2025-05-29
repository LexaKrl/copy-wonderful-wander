package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkRecordingApi;
import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.wwwalkservice.service.service_interface.RecordLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class WalkRecordingController implements WalkRecordingApi {

    private final RecordLocationService locationService;

    @Override
    public SseEmitter subscribe(UUID walkId) {
        return locationService.subscribe(walkId);
    }

    @Override
    public ResponseEntity<Void> streamData(UUID walkId, WalkDataRequest walkDataRequest) {
        return locationService.streamData(walkId, walkDataRequest);
    }
}
