package com.technokratos.handler;

import com.technokratos.event.AvatarSavedEvent;
import com.technokratos.service.UserService;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = KafkaTopics.USER_AVATAR_SAVED_TOPIC)
public class AvatarSavedEventHandler {
    private final UserService userService;

    @KafkaHandler
    public void handle(AvatarSavedEvent avatarSavedEvent) {
        log.info("Handle avatar saved event - {}", avatarSavedEvent);
        userService.saveAvatarFilename(
                avatarSavedEvent.getOwnerId(),
                avatarSavedEvent.getAvatarFilename()
        );
    }
}
