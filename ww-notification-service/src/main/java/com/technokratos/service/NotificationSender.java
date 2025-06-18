package com.technokratos.service;

import com.technokratos.entity.UserData;
import com.technokratos.templates.NotificationTemplate;

import java.util.List;
import java.util.Map;

public interface NotificationSender {
    void sendNotification(UserData userData, NotificationTemplate template, Map<String, String> vars);
    void sendManyNotifications(List<UserData> userData, NotificationTemplate template, Map<String, String> vars);
}
