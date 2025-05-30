package com.technokratos.producer;

import com.technokratos.event.AvatarSavedEvent;
import com.technokratos.event.PhotoOfWalkSavedEvent;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PhotoSavedProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAvatarSavedEvent(UUID ownerId, String avatarFilename) {
        kafkaTemplate.send(
                KafkaTopics.USER_AVATAR_SAVED_TOPIC,
                AvatarSavedEvent.builder()
                        .ownerId(ownerId)
                        .avatarFilename(avatarFilename)
                        .build());
    }

    public void sendPhotoOfWalkSavedEvent(UUID walkId, UUID ownerId, String photoOfWalkFilename) {
        kafkaTemplate.send(
                KafkaTopics.PHOTO_OF_WALK_SAVED_TOPIC,
                PhotoOfWalkSavedEvent.builder()
                        .walkId(walkId)
                        .ownerId(ownerId)
                        .photoFilename(photoOfWalkFilename)
                        .build()
        );
    }
}