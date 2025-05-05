package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkRecordingApi;
import com.technokratos.dto.request.walk.WalkDataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class WalkRecordingController implements WalkRecordingApi {



    @Override
    public ResponseEntity<Void> subscribe(String walkId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> unsubscribe(String walkId) {

        return ResponseEntity.noContent().build();
    }

    @Override
    public void streamData(UUID walkId, WalkDataRequest walkDataRequest) {

    }
}
