package com.technokratos.handler;

import com.technokratos.event.WalkInviteEvent;
import com.technokratos.service.impl.BaseUserDataService;
import com.technokratos.service.impl.MailSendService;
import com.technokratos.util.KafkaTopics;
import com.technokratos.util.MailUtils;
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

    private final MailSendService mailSendService;
    private final BaseUserDataService baseUserDataService;

    @KafkaHandler
    public void handle(WalkInviteEvent walkInviteEvent) {
        log.info("Notification service received WalkInviteEvent with walk id: {}", walkInviteEvent.getWalkId());
        mailSendService.sendEmail(
                baseUserDataService.getUserDataByUserId(walkInviteEvent.getParticipantId()).getEmail(),  /* TODO define where take email */
                MailUtils.WALK_INVITATION_SUBJECT,
                Map.of("title", walkInviteEvent.getWalkId(), "context", walkInviteEvent.getWalkId()) /* TODO change this map */
        );
    }
}
