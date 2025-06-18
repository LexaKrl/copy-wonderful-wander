package com.technokratos.handler;

import com.technokratos.event.PhotoOfWalkSavedEvent;
import com.technokratos.util.KafkaTopics;
import com.technokratos.service.impl.BaseRecordLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = KafkaTopics.PHOTO_OF_WALK_SAVED_TOPIC)
public class PhotoOfWalkEventHandler {

    private final BaseRecordLocationService baseRecordLocationService;

    @KafkaHandler
    public void handlePhotoOfWalkSavedTopic(PhotoOfWalkSavedEvent photoOfWalkSavedEvent) {
        log.info("Walk service accepted photo with walk id: {}", photoOfWalkSavedEvent.getWalkId());
        baseRecordLocationService.addPhotoUrls(
                photoOfWalkSavedEvent.getWalkId(),
                photoOfWalkSavedEvent.getPhotoFilename()
        );
    }
}
