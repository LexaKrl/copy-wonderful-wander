package com.technokratos.handler;

import com.technokratos.client.UserServiceClient;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.entity.UserData;
import com.technokratos.enums.notification.NotificationChannel;
import com.technokratos.enums.notification.NotificationType;
import com.technokratos.event.WalkFinishedEvent;
import com.technokratos.service.impl.BaseUserDataService;
import com.technokratos.service.impl.NotificationService;
import com.technokratos.util.EventUtils;
import com.technokratos.util.RabbitUtilities;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Component
@RequiredArgsConstructor
public class WalkNotificationHandler {
    private final UserServiceClient userServiceClient;
    private final BaseUserDataService baseUserDataService;
    private final EventUtils eventUtils;
    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitUtilities.WALK_FINISHED_QUEUE)
    public void handleWalkFinishedEvent(WalkFinishedEvent walkFinishedEvent) {
        log.info("Handle walk notification for walk with id: {}", walkFinishedEvent.getWalkId());

        PageResponse<UserCompactResponse> followersPage =
                userServiceClient.getFollowersByUserId(walkFinishedEvent.getWalkOwnerId(), 1, Integer.MAX_VALUE);
        if (followersPage == null || followersPage.data().isEmpty()) return;

        List<UserData> followers = followersPage.data().stream()
                .map(UserCompactResponse::userId)
                .map(baseUserDataService::getUserDataByUserId)
                .toList();

        Map<String, String> vars = eventUtils.prepareWalkFinishVariables(walkFinishedEvent);

        notificationService.sendMany(
                NotificationType.WALK_FINISHED,
                NotificationChannel.ALL,
                followers,
                vars
        );
    }

    /* TODO add another cases */
}
