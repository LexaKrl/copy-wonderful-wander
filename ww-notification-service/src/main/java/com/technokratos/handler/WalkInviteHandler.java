package com.technokratos.handler;

import com.technokratos.event.WalkInviteEvent;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = KafkaTopics.WALK_INVITE_TOPIC)
public class WalkInviteHandler {

    @KafkaHandler
    public void handle(WalkInviteEvent walkInviteEvent) {
        log.info("Notification service received WalkInviteEvent with walk id: {}", walkInviteEvent.getWalkId());
        /* TODO define implementation */
    }
}
