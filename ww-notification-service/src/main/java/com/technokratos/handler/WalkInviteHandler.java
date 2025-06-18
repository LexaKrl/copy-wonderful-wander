package com.technokratos.handler;

import com.technokratos.entity.UserData;
import com.technokratos.enums.notification.NotificationChannel;
import com.technokratos.enums.notification.NotificationType;
import com.technokratos.event.WalkInviteEvent;
import com.technokratos.service.impl.BaseUserDataService;
import com.technokratos.service.impl.NotificationService;
import com.technokratos.util.EventUtils;
import com.technokratos.util.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = KafkaTopics.WALK_INVITE_TOPIC)
public class WalkInviteHandler {

    private final BaseUserDataService baseUserDataService;
    private final EventUtils eventUtils;
    private final NotificationService notificationService;

    @KafkaHandler
    public void handle(WalkInviteEvent walkInviteEvent) {
        log.info("Notification service received WalkInviteEvent with walk id: {}", walkInviteEvent.getWalkId());

        UserData recipient = baseUserDataService.getUserDataByUserId(walkInviteEvent.getParticipantId());
        Map<String, String> vars = eventUtils.prepareInvitationVariables(walkInviteEvent);

        notificationService.send(
                NotificationType.WALK_INVITE,
                NotificationChannel.ALL,
                recipient,
                vars
        );
    }
}
