package com.technokratos.handler;

import com.technokratos.entity.UserData;
import com.technokratos.event.FcmTokenReceivedEvent;
import com.technokratos.event.UserCreatedEvent;
import com.technokratos.event.UserDeletedEvent;
import com.technokratos.event.UserUpdatedEvent;
import com.technokratos.service.UserDataService;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {
        KafkaTopics.USER_CREATED_TOPIC, KafkaTopics.USER_UPDATED_TOPIC, KafkaTopics.USER_DELETED_TOPIC,
        KafkaTopics.FCM_TOKEN_RECEIVED_TOPIC
})
public class UserDataHandler {

    private final UserDataService userDataService;

    @KafkaHandler
    public void handleUserCreated(UserCreatedEvent userCreatedEvent) {
        log.info("User data created: {}", userCreatedEvent);
        userDataService.saveUserData(
                new UserData(
                        userCreatedEvent.getUserId(),
                        userCreatedEvent.getEmail(),
                        null
                )
        );
    }

    @KafkaHandler
    public void handleUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        log.info("User data updated: {}", userUpdatedEvent);
        UUID userId = userUpdatedEvent.getUserId();
        userDataService.updateUserData(
                new UserData(
                        userId,
                        userUpdatedEvent.getEmail(),
                        userDataService.getFcmToken(userId)
                )
        );
    }

    @KafkaHandler
    public void handleUserDeleted(UserDeletedEvent userDeletedEvent) {
        log.info("User data deleted: {}", userDeletedEvent);
        userDataService.deleteUserData(userDeletedEvent.getUserId());
    }

    @KafkaHandler
    public void handleFcmTokenReceived(FcmTokenReceivedEvent fcmTokenReceivedEvent) {
        log.info("FCM token received in notification service: {}", fcmTokenReceivedEvent);
        userDataService.updateFcmToken(fcmTokenReceivedEvent.getUserId(), fcmTokenReceivedEvent.getFcmToken());
    }
}
