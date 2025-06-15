package com.technokratos.wwwalkservice.service.impl;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.enums.walk.WalkStatus;
import com.technokratos.wwwalkservice.config.properties.WalkRecordLocationProperties;
import com.technokratos.wwwalkservice.entity.Walk;
import com.technokratos.wwwalkservice.entity.WalkLocationData;
import com.technokratos.wwwalkservice.exception.WalkAccessDeniedException;
import com.technokratos.wwwalkservice.exception.WalkNotFoundException;
import com.technokratos.wwwalkservice.exception.WalkStreamDataException;
import com.technokratos.wwwalkservice.exception.WalkSubscriptionException;
import com.technokratos.wwwalkservice.mapper.WalkLocationRecordMapper;
import com.technokratos.wwwalkservice.repository.WalkRecordLocationRepository;
import com.technokratos.wwwalkservice.repository.WalkRepository;
import com.technokratos.wwwalkservice.service.RecordLocationService;
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
    private final WalkLocationRecordMapper walkLocationRecordMapper;
    private final WalkRepository walkRepository;
    private final WalkRecordLocationProperties walkProperties;

    private Map<UUID, List<SseEmitter>> subscribers = new ConcurrentHashMap<>();
    private Map<UUID, Instant> lastUpdate = new ConcurrentHashMap<>();

    private static final String SUBSCRIBE_EVENT = "SUBSCRIBED";
    private static final String STREAM_DATA_EVENT = "STREAM_DATA";
    private static final String SUCCESS_SUBSCRIPTION_MESSAGE = "Successfully subscribed";

    @Override
    public SseEmitter subscribe(UUID walkId) {
        if (!baseWalkService.isParticipant(walkId)) {throw new WalkAccessDeniedException(walkId);}
        Walk targetWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!targetWalk.getWalkStatus().equals(WalkStatus.STARTED)) {throw new WalkSubscriptionException(walkId);}
        /* TODO notification user subscribed on walk */
        return createEmitter(walkId);
    }

    @Override
    public void streamData(UUID walkId, WalkDataRequest walkDataRequest) {
        if (!baseWalkService.isOwner(walkId)) {throw new WalkAccessDeniedException(walkId);}
        Walk targetWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        checkWalkStatus(targetWalk);
        saveData(walkId, walkDataRequest);
        broadcastData(walkId, walkDataRequest);
    }

    @Override
    public void finishWalk(UUID walkId) {
        if (!baseWalkService.isOwner(walkId)) {throw new WalkAccessDeniedException(walkId);}
        WalkLocationData walkLocationData = walkRecordLocationRepository.findByWalkId(walkId).orElseGet(WalkLocationData::new);
        baseWalkService.finishWalk(walkId, walkLocationData);

        List<SseEmitter> emitters = subscribers.get(walkId);
        if (emitters != null) {emitters.forEach(ResponseBodyEmitter::complete);}
        subscribers.remove(walkId);
        lastUpdate.remove(walkId);

        walkRecordLocationRepository.deleteByWalkId(walkId);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000L)
    public void forcedWalkFinish() {
        Instant now = Instant.now();
        List<UUID> expiredWalks = lastUpdate.entrySet().stream()
                .filter(entry -> Duration.between(entry.getValue(), now).toMinutes() > 3)
                .map(Map.Entry::getKey)
                .toList();

        expiredWalks.forEach(this::finishWalk);
    }

    public void addPhotoUrls() {
        /* TODO implement adding photo into STARTED walk */
        /* Firstly add to photo list to broadcast them to other walk participants */
        /* Secondly add photos UUIDs into walk_photo list and when someone requesting photo return them presignUrl */
    }

    private SseEmitter createEmitter(UUID walkId) {
        SseEmitter emitter = new SseEmitter(walkProperties.getMaxSseTimeout());

        subscribers.computeIfAbsent(walkId, id -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(walkId, emitter));
        emitter.onTimeout(() -> removeEmitter(walkId, emitter));
        emitter.onError(e -> removeEmitter(walkId, emitter));

        try {
            emitter.send(SseEmitter.event()
                    .name(SUBSCRIBE_EVENT)
                    .data(SUCCESS_SUBSCRIPTION_MESSAGE)
                    .reconnectTime(walkProperties.getReconnectionTime())
                    .build()
            );
        } catch (IOException e) {
            removeEmitter(walkId, emitter);
        }

        return emitter;
    }

    private void broadcastData(UUID walkId, WalkDataRequest walkDataRequest) {
        List<SseEmitter> emitters = subscribers.get(walkId);
        if (emitters != null) {
            emitters.forEach(emitter -> {
               try {
                   emitter.send(SseEmitter.event()
                           .name(STREAM_DATA_EVENT)
                           .data(walkDataRequest)
                           .reconnectTime(walkProperties.getReconnectionTime())
                           .build()
                   );
               } catch (IOException e) {
                   removeEmitter(walkId, emitter);
               }
            });
        }
    }

    private void saveData(UUID walkId, WalkDataRequest walkDataRequest) {
        WalkLocationData walkLocationData = walkRecordLocationRepository.findByWalkId(walkId).orElseGet(() -> createWalkLocationData(walkId));
        walkLocationData.getPoints().add(walkLocationRecordMapper.fromRequest(walkDataRequest));
        walkRecordLocationRepository.save(walkLocationData);
    }

    private WalkLocationData createWalkLocationData(UUID walkId) {
        WalkLocationData walkLocationData = WalkLocationData.builder()
                .walkId(walkId)
                .build();
        return walkRecordLocationRepository.save(walkLocationData);
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

    private void checkWalkStatus(Walk targetWalk) {
        switch (targetWalk.getWalkStatus()) {
            case STARTED:
                break;
            case NOT_STARTED:
                targetWalk.setWalkStatus(WalkStatus.STARTED);
                walkRepository.save(targetWalk);
                break;
            case FINISHED:
                throw new WalkStreamDataException(targetWalk.getWalkId());
        }
    }
}
