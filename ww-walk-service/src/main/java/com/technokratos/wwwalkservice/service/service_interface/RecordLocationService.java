package com.technokratos.wwwalkservice.service.service_interface;

import com.technokratos.dto.request.walk.WalkDataRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public interface RecordLocationService {


    SseEmitter subscribe(UUID walkId);


    ResponseEntity<Void> streamData(UUID walkId, WalkDataRequest walkDataRequest);
}
