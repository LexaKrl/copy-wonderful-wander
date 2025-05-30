package com.technokratos.wwwalkservice.service;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.wwwalkservice.entity.WalkLocationData;
import com.technokratos.wwwalkservice.exception.WalkAccessDeniedException;
import com.technokratos.wwwalkservice.exception.WalkRecordDataNotFoundException;
import com.technokratos.wwwalkservice.repository.WalkRecordLocationRepository;
import com.technokratos.wwwalkservice.service.service_interface.RecordLocationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Service
@RequiredArgsConstructor
public class BaseRecordLocationService implements RecordLocationService {

    private final BaseWalkService baseWalkService;
    private final WalkRecordLocationRepository walkRecordLocationRepository;

    private Map<UUID, List<SseEmitter>> subscribers = new ConcurrentHashMap<>();
    private Map<UUID, Instant> lastUpdate = new ConcurrentHashMap<>();

    private static final long MAX_SSE_TIMEOUT = 30 * 60 * 1000L;
    private static final long RECONNECTION_TIME = 3 * 1000L;

    @Override
    public SseEmitter subscribe(UUID walkId) {
        if (!baseWalkService.isParticipant(walkId)) {throw new WalkAccessDeniedException(walkId);}
        return createEmitter(walkId);
    }

    @Override
    public void streamData(UUID walkId, WalkDataRequest walkDataRequest) {
        if (!baseWalkService.isOwner(walkId)) {throw new WalkAccessDeniedException(walkId);}
        saveData(walkId, walkDataRequest); /* TODO implement method */
        broadcastData(walkId, walkDataRequest); /* TODO implement method */
    }

    @Override
    public void finishWalk(UUID walkId) {
        if (!baseWalkService.isOwner(walkId)) {throw new WalkAccessDeniedException(walkId);}
        WalkLocationData walkLocationData = walkRecordLocationRepository.findByWalkId(walkId).orElseGet(WalkLocationData::new);
        /* TODO implement this method */
        baseWalkService.finishWalk(walkId);

        subscribers.get(walkId).forEach(ResponseBodyEmitter::complete);
        subscribers.remove(walkId);
        lastUpdate.remove(walkId);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000L)
    public void forcedWalkFinish() {
        Instant now = Instant.now();
        lastUpdate.forEach((walkId, updated) -> {
            if (Duration.between(updated, now).toMinutes() > 3) {
                finishWalk(walkId);
            }
        });
    }

    private SseEmitter createEmitter(UUID walkId) {
        SseEmitter emitter = new SseEmitter(MAX_SSE_TIMEOUT);

        subscribers.computeIfAbsent(walkId, id -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(walkId, emitter));
        emitter.onTimeout(() -> removeEmitter(walkId, emitter));
        emitter.onError(e -> removeEmitter(walkId, emitter));

        try {
            emitter.send(SseEmitter.event()
                    .name("SUBSCRIBED")
                    .data("Successfully subscribed on walk")
                    .reconnectTime(RECONNECTION_TIME)
                    .build()
            );
        } catch (IOException e) {
            removeEmitter(walkId, emitter);
        }

        return emitter;
    }

    private void broadcastData(UUID walkId, WalkDataRequest walkDataRequest) {

    }

    private void saveData(UUID walkId, WalkDataRequest walkDataRequest) {

    }

    private void removeEmitter(UUID walkId, SseEmitter emitter) {
        List<SseEmitter> emitterList = subscribers.get(walkId);
        if (emitterList != null) {
            emitterList.remove(emitter);
            if (emitterList.isEmpty()) {
                subscribers.remove(walkId);
            }
        }
    }
}
