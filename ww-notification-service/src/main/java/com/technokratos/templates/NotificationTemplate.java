package com.technokratos.templates;

import com.technokratos.enums.notification.NotificationType;

import java.util.Map;

public interface NotificationTemplate {
    NotificationType getType();
    String getTitle(Map<String, String> vars);
    String getBody(Map<String, String> vars);
    String getActionUrl(Map<String, String> vars); // если есть
}
