package com.technokratos.service.impl;

import com.google.firebase.messaging.*;
import com.technokratos.entity.UserData;
import com.technokratos.exception.SendMessageException;
import com.technokratos.service.NotificationSender;
import com.technokratos.templates.NotificationTemplate;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseSendService implements NotificationSender {

    @Async
    @Retryable(
            retryFor = { MessagingException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public void sendNotification(UserData userData, NotificationTemplate template, Map<String, String> vars) {
        try {
            if (userData == null) {log.warn("UserData is null"); return;}
            if (userData.getFcmToken() == null || userData.getFcmToken().isBlank()) {
                log.warn("Empty FCM token for user: {}", userData.getUserId());
                return;
            }

            Message message = Message.builder()
                    .setToken(userData.getFcmToken())
                    .setNotification(Notification.builder()
                            .setTitle(template.getTitle(vars))
                            .setBody(template.getBody(vars))
                            .build())
                    .putAllData(vars)
                    .putData("actionUrl", template.getActionUrl(vars))
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Firebase notification sent: {}", response);
        } catch (FirebaseMessagingException e) {
            throw new SendMessageException("Error while sending FCM notification");
        }
    }

    @Override
    public void sendManyNotifications(List<UserData> userDataList, NotificationTemplate template, Map<String, String> vars) {
        List<String> tokens = userDataList.stream().map(UserData::getFcmToken).toList();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(Notification.builder()
                        .setTitle(template.getTitle(vars))
                        .setBody(template.getBody(vars))
                        .build()
                )
                .putAllData(vars)
                .putData("actionUrl", template.getActionUrl(vars))
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            log.info("Firebase multicast sent. Success: {}", response.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            throw new SendMessageException("Firebase multicast error: %s".formatted(e.getMessage()));
        }
    }
}
