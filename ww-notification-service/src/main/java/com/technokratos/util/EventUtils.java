package com.technokratos.util;

import com.technokratos.event.WalkFinishedEvent;
import com.technokratos.event.WalkInviteEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventUtils {

    public Map<String, String> prepareInvitationVariables(WalkInviteEvent walkInviteEvent) {
        String link = walkInviteEvent.getWalkAcceptationUrl()
                .replace("{walkId}", walkInviteEvent.getWalkId().toString())
                .replace("{participantId}", walkInviteEvent.getParticipantId().toString())
                .replace("{acceptationToken}", walkInviteEvent.getAcceptationToken().toString());

        return Map.of(
                "title", MessagingUtils.WALK_INVITATION_TITLE,
                "message", MessagingUtils.WALK_INVITATION_MESSAGE,
                "link", link,
                "walkId", walkInviteEvent.getWalkId().toString()
        );
    }

    public Map<String, String> prepareWalkFinishVariables(WalkFinishedEvent walkFinishedEvent) {
        return Map.of(
            "title", MessagingUtils.WALK_FINISH_TITLE,
            "message", MessagingUtils.WALK_FINISH_MESSAGE,
            "walkOwnerId", walkFinishedEvent.getWalkOwnerId().toString(),
            "walkName", walkFinishedEvent.getWalkName(),
            "walkId", walkFinishedEvent.getWalkId().toString()
        );
    }
}
