package com.technokratos.enums.notification;

import java.util.Arrays;
import java.util.List;

public enum NotificationChannel {
    EMAIL,
    FIREBASE,
    ALL;

    public static List<NotificationChannel> getNotificationChannels() {
        return Arrays.stream(values())
                .filter(channel -> channel != ALL)
                .toList();
    }
}
