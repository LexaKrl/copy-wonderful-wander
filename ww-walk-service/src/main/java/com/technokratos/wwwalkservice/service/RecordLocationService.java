package com.technokratos.wwwalkservice.service;

import com.technokratos.dto.request.walk.WalkDataRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public interface RecordLocationService {


    SseEmitter subscribe(UUID walkId);

    void streamData(UUID walkId, WalkDataRequest walkDataRequest);

    void finishWalk(UUID walkId);
}
