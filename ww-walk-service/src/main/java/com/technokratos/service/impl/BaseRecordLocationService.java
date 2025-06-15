package com.technokratos.service.impl;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.dto.response.walk.WalkDataResponse;
import com.technokratos.enums.walk.WalkStatus;
import com.technokratos.service.MinioService;
import com.technokratos.config.properties.WalkRecordLocationProperties;
import com.technokratos.entity.Walk;
import com.technokratos.entity.WalkLocationData;
import com.technokratos.exception.WalkAccessDeniedException;
import com.technokratos.exception.WalkNotFoundException;
import com.technokratos.exception.WalkStreamDataException;
import com.technokratos.exception.WalkSubscriptionException;
import com.technokratos.mapper.WalkLocationRecordMapper;
import com.technokratos.repository.WalkRecordLocationRepository;
import com.technokratos.repository.WalkRepository;
import com.technokratos.service.RecordLocationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
    private final MinioService minioService;

    private Map<UUID, List<SseEmitter>> subscribers = new ConcurrentHashMap<>();
    private Map<UUID, Instant> lastUpdate = new ConcurrentHashMap<>();
    private Map<UUID, List<String>> photosToAdd = new ConcurrentHashMap<>();

    private static final String SUBSCRIBE_EVENT = "SUBSCRIBED";
    private static final String STREAM_DATA_EVENT = "STREAM_DATA";
    private static final String SUCCESS_SUBSCRIPTION_MESSAGE = "Successfully subscribed";

    @Override
    public SseEmitter subscribe(UUID currentUserId, UUID walkId) {
        if (!baseWalkService.isParticipant(currentUserId, walkId)) {throw new WalkAccessDeniedException(walkId);}
        Walk targetWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!targetWalk.getWalkStatus().equals(WalkStatus.STARTED)) {throw new WalkSubscriptionException(walkId);}
        return createEmitter(walkId);
    }

    @Override
    public void streamData(UUID currentUserId, UUID walkId, WalkDataRequest walkDataRequest) {
        if (!baseWalkService.isOwner(currentUserId, walkId)) {throw new WalkAccessDeniedException(walkId);}
        Walk targetWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        checkWalkStatus(targetWalk);
        saveData(walkId, walkDataRequest);
        lastUpdate.put(walkId, Instant.now());

        broadcastData(walkId, walkDataRequest);
    }

    @Override
    public void finishWalk(UUID currentUserId, UUID walkId) {
        if (!baseWalkService.isOwner(currentUserId, walkId)) {throw new WalkAccessDeniedException(walkId);}
        WalkLocationData walkLocationData = walkRecordLocationRepository.findByWalkId(walkId).orElseGet(WalkLocationData::new);
        baseWalkService.finishWalk(currentUserId, walkId, walkLocationData);

        List<SseEmitter> emitters = subscribers.get(walkId);
        if (emitters != null) {emitters.forEach(ResponseBodyEmitter::complete);}

        cleanWalkDataById(walkId);
    }

    private void cleanWalkDataById(UUID walkId) {
        subscribers.remove(walkId);
        lastUpdate.remove(walkId);
        photosToAdd.remove(walkId);

        walkRecordLocationRepository.deleteByWalkId(walkId);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000L)
    public void forcedWalkFinish() {
        Instant now = Instant.now();
        List<UUID> expiredWalks = lastUpdate.entrySet().stream()
                .filter(entry -> Duration.between(entry.getValue(), now).toMinutes() > walkProperties.getMaxWalkExpiresTime())
                .map(Map.Entry::getKey)
                .toList();

        expiredWalks.forEach(this::cleanWalkDataById);
    }

    public void addPhotoUrls(UUID walkId, String photoFilename) {
        photosToAdd.computeIfAbsent(walkId, id -> new CopyOnWriteArrayList<>()).add(photoFilename);
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
        List<String> photos = new ArrayList<>();
        List<SseEmitter> emitters = subscribers.get(walkId);

        List<String> photosQueries = photosToAdd.getOrDefault(walkId, List.of());
        photosToAdd.remove(walkId);

        photosQueries.forEach(query -> photos.add(minioService.getPresignedUrl(query)));

        WalkDataResponse walkDataResponse = new WalkDataResponse(
                walkDataRequest.longitude(),
                walkDataRequest.latitude(),
                walkDataRequest.steps(),
                walkDataRequest.meters(),
                photos,
                walkDataRequest.time()
        );

        if (emitters != null) {
            emitters.forEach(emitter -> {
               try {
                   emitter.send(SseEmitter.event()
                           .name(STREAM_DATA_EVENT)
                           .data(walkDataResponse)
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
        WalkLocationData walkLocationData = walkRecordLocationRepository.findByWalkId(walkId).orElseGet(() -> WalkLocationData.builder().walkId(walkId).build());
        walkLocationData.getPoints().add(walkLocationRecordMapper.fromRequest(walkDataRequest));
        walkRecordLocationRepository.save(walkLocationData);
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
