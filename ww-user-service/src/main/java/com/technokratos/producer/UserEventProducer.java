package com.technokratos.producer;

import com.technokratos.event.FcmTokenReceivedEvent;
import com.technokratos.event.UserCreatedEvent;
import com.technokratos.event.UserDeletedEvent;
import com.technokratos.event.UserUpdatedEvent;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        kafkaTemplate.send(
                KafkaTopics.USER_CREATED_TOPIC,
                userCreatedEvent
        );
    }

    public void sendUserUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
        kafkaTemplate.send(
                KafkaTopics.USER_UPDATED_TOPIC,
                userUpdatedEvent
        );
    }

    public void sendUserDeletedEvent(UserDeletedEvent userDeletedEvent) {
        kafkaTemplate.send(
                KafkaTopics.USER_DELETED_TOPIC,
                userDeletedEvent
        );
    }

    public void sendFcmTokenReceivedEvent(FcmTokenReceivedEvent fcmTokenReceivedEvent) {
        kafkaTemplate.send(
                KafkaTopics.FCM_TOKEN_RECEIVED_TOPIC,
                fcmTokenReceivedEvent
        );
    }
}
