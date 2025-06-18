package com.technokratos.producer;

import com.technokratos.event.WalkFinishedEvent;
import com.technokratos.event.WalkInviteEvent;
import com.technokratos.util.KafkaTopics;
import com.technokratos.util.RabbitUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalkEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    public void sendWalkInviteEvent(WalkInviteEvent walkInviteEvent) {
        kafkaTemplate.send(
                KafkaTopics.WALK_INVITE_TOPIC,
                walkInviteEvent
        );
    }

    public void sendWalkFinishedEvent(WalkFinishedEvent walkFinishedEvent) {
        rabbitTemplate.convertAndSend(
                RabbitUtilities.NOTIFICATION_EXCHANGE,
                RabbitUtilities.WALK_FINISHED_QUEUE,
                walkFinishedEvent
        );
    }
}
