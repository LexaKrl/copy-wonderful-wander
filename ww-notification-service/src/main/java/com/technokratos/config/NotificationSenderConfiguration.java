package com.technokratos.config;

import com.technokratos.enums.notification.NotificationChannel;
import com.technokratos.service.NotificationSender;
import com.technokratos.service.impl.FirebaseSendService;
import com.technokratos.service.impl.MailSendService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
public class NotificationSenderConfiguration {

    @Bean
    public Map<NotificationChannel, NotificationSender> notificationSenders(
            MailSendService emailSender,
            FirebaseSendService firebaseSender
    ) {
        Map<NotificationChannel, NotificationSender> map = new EnumMap<>(NotificationChannel.class);
        map.put(NotificationChannel.EMAIL, emailSender);
        map.put(NotificationChannel.FIREBASE, firebaseSender);
        return map;
    }
}