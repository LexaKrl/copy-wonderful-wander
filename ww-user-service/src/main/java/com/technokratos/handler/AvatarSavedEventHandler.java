package com.technokratos.handler;

import com.technokratos.event.AvatarSavedEvent;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "user.avatar.saved")
public class AvatarSavedEventHandler {
    private final UserService userService;

    @KafkaHandler
    public void handle(AvatarSavedEvent avatarSavedEvent) {
        log.info(String.valueOf(avatarSavedEvent));
        userService.saveAvatarFilename(
                avatarSavedEvent.getOwnerId(),
                avatarSavedEvent.getAvatarFilename()
        );
    }
}
