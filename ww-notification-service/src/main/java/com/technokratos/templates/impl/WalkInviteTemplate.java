package com.technokratos.templates.impl;

import com.technokratos.config.property.ApplicationUrlProperties;
import com.technokratos.enums.notification.NotificationType;
import com.technokratos.templates.NotificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WalkInviteTemplate implements NotificationTemplate {
    private final ApplicationUrlProperties applicationUrlProperties;

    @Override
    public NotificationType getType() {
        return NotificationType.WALK_INVITE;
    }

    @Override
    public String getTitle(Map<String, String> vars) {
        return "New Walk Invite";
    }

    @Override
    public String getBody(Map<String, String> vars) {
        return "User %s invites you to walk".formatted(vars.get("sender"));
    }

    @Override
    public String getActionUrl(Map<String, String> vars) {
        return String.format(applicationUrlProperties.getBaseUrl(), vars.get("sender"));
    }
}
