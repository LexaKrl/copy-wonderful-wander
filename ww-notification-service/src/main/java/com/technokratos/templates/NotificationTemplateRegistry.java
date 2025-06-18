package com.technokratos.templates;

import com.technokratos.enums.notification.NotificationType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class NotificationTemplateRegistry {
    private final Map<NotificationType, NotificationTemplate> templates = new EnumMap<>(NotificationType.class);

    public NotificationTemplateRegistry(List<NotificationTemplate> allTemplates) {
        for (NotificationTemplate template : allTemplates) {
            templates.put(template.getType(), template);
        }
    }

    public NotificationTemplate getTemplate(NotificationType type) {
        return templates.get(type);
    }
}
