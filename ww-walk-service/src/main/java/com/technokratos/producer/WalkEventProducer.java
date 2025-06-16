package com.technokratos.producer;

import com.technokratos.event.WalkInviteEvent;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalkEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendWalkInviteEvent(WalkInviteEvent walkInviteEvent) {
        kafkaTemplate.send(
                KafkaTopics.WALK_INVITE_TOPIC,
                walkInviteEvent
        );
    }
}
