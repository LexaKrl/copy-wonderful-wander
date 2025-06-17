package com.technokratos.handler;

import com.technokratos.entity.UserData;
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

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {KafkaTopics.USER_CREATED_TOPIC, KafkaTopics.USER_UPDATED_TOPIC, KafkaTopics.USER_DELETED_TOPIC})
public class UserDataHandler {

    private final UserDataService userDataService;

    @KafkaHandler
    public void handleUserCreated(UserCreatedEvent userCreatedEvent) {
        log.info("User data created: {}", userCreatedEvent);
        userDataService.saveUserData(
                new UserData(
                        userCreatedEvent.getUserId(),
                        userCreatedEvent.getEmail(),
                        userCreatedEvent.getEmail() /* TODO add FCM token */
                )
        );
    }

    @KafkaHandler
    public void handleUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        log.info("User data updated: {}", userUpdatedEvent);
        userDataService.updateUserData(
                new UserData(
                        userUpdatedEvent.getUserId(),
                        userUpdatedEvent.getEmail(),
                        userUpdatedEvent.getEmail() /* TODO add FCM token */
                )
        );
    }

    @KafkaHandler
    public void handleUserDeleted(UserDeletedEvent userDeletedEvent) {
        log.info("User data deleted: {}", userDeletedEvent);
        userDataService.deleteUserData(userDeletedEvent.getUserId());
    }
}
