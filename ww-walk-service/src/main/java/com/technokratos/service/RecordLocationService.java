package com.technokratos.service;

import com.technokratos.dto.request.walk.WalkDataRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public interface RecordLocationService {


    SseEmitter subscribe(UUID currentUserId, UUID walkId);

    void streamData(UUID currentUserId, UUID walkId, WalkDataRequest walkDataRequest);

    void finishWalk(UUID currentUserId, UUID walkId);
}
