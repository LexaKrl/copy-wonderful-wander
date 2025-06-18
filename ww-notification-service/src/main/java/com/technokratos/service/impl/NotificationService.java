package com.technokratos.service.impl;

import com.technokratos.entity.UserData;
import com.technokratos.enums.notification.NotificationChannel;
import com.technokratos.enums.notification.NotificationType;
import com.technokratos.service.NotificationSender;
import com.technokratos.templates.NotificationTemplate;
import com.technokratos.templates.NotificationTemplateRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationTemplateRegistry templateRegistry;
    private final Map<NotificationChannel, NotificationSender> senderMap;

    public void send(
            NotificationType type,
            NotificationChannel channel,
            UserData recipient,
            Map<String, String> vars
    ) {
        NotificationTemplate template = templateRegistry.getTemplate(type);

        List<NotificationChannel> channels = (channel == NotificationChannel.ALL)
                ? NotificationChannel.getNotificationChannels()
                : List.of(channel);

        for (NotificationChannel c : channels) {
            NotificationSender sender = senderMap.get(c);
            if (sender != null) {
                sender.sendNotification(recipient, template, vars);
            }
        }
    }

    public void sendMany(
            NotificationType type,
            NotificationChannel channel,
            List<UserData> recipients,
            Map<String, String> vars
    ) {
        NotificationTemplate template = templateRegistry.getTemplate(type);

        List<NotificationChannel> channels = (channel == NotificationChannel.ALL)
                ? NotificationChannel.getNotificationChannels()
                : List.of(channel);

        for (NotificationChannel c : channels) {
            NotificationSender sender = senderMap.get(c);
            if (sender != null) {
                sender.sendManyNotifications(recipients, template, vars);
            }
        }
    }
}