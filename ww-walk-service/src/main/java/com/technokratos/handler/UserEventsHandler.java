package com.technokratos.handler;

import com.technokratos.event.UserCreatedEvent;
import com.technokratos.event.UserDeletedEvent;
import com.technokratos.util.KafkaTopics;
import com.technokratos.entity.UserWalkVisibility;
import com.technokratos.service.impl.BaseWalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {KafkaTopics.USER_CREATED_TOPIC, KafkaTopics.USER_DELETED_TOPIC})
public class UserEventsHandler {
    private final BaseWalkService walkService;

    @KafkaHandler
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        log.info("Walk service accepted event: {}", userCreatedEvent);
        walkService.saveWalkVisibility(UserWalkVisibility.builder()
                        .userId(userCreatedEvent.getUserId())
                        .walkVisibility(userCreatedEvent.getWalkVisibility())
                        .build()
        );
    }

    @KafkaHandler
    public void handleUserDeletedEvent(UserDeletedEvent userDeletedEvent) {
        log.info("Walk service accepted event: {}", userDeletedEvent);
        walkService.deleteWalkVisibility(userDeletedEvent.getUserId());
    }
}
