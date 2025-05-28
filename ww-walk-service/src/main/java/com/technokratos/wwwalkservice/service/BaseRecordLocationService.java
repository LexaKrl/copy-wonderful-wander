package com.technokratos.wwwalkservice.service;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.wwwalkservice.service.service_interface.RecordLocationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class BaseRecordLocationService implements RecordLocationService {

    private Map<UUID, List<SseEmitter>> emitters = new HashMap<>();

    @Override
    public SseEmitter subscribe(UUID walkId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> streamData(UUID walkId, WalkDataRequest walkDataRequest) {
        return null;
    }


}
