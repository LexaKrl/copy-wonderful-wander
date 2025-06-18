package com.technokratos.handler;

import com.technokratos.event.AvatarSavedEvent;
import com.technokratos.event.FriendshipUpdateEvent;
import com.technokratos.event.UserCreatedEvent;
import com.technokratos.event.UserUpdatedEvent;
import com.technokratos.service.UserService;
import com.technokratos.util.KafkaTopics;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {


    private final UserService userService;
    private final UserMapper userMapper;

    @KafkaListener(topics = KafkaTopics.USER_CREATED_TOPIC)
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        userService.save(
                userMapper.toCachedUserEntity(
                        userCreatedEvent,
                        String.valueOf(userCreatedEvent.getUserId()
                        )
                )
        );
    }

    @KafkaListener(topics = KafkaTopics.USER_UPDATED_TOPIC)
    public void handleUserUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
        userService.update(
                userMapper.toCachedUserEntity(
                        userUpdatedEvent,
                        String.valueOf(userUpdatedEvent.getUserId()
                        )
                )
        );
    }

    @KafkaListener(topics = KafkaTopics.USER_AVATAR_SAVED_TOPIC)
    public void handleAvatarSavedEvent(AvatarSavedEvent avatarSavedEvent) {
        userService.updateAvatarUrl(
                String.valueOf(avatarSavedEvent.getOwnerId()),
                avatarSavedEvent.getAvatarFilename()

        );
    }

    @KafkaListener(topics = KafkaTopics.USER_FRIENDSHIP_UPDATE)
    public void handleAvatarSavedEvent(FriendshipUpdateEvent friendshipUpdateEvent) {
        String userId = String.valueOf(friendshipUpdateEvent.getUserId());
        String friendId = String.valueOf(friendshipUpdateEvent.getFriendId());

        if (friendshipUpdateEvent.getType() == FriendshipUpdateEvent.FriendshipEventType.ADD) {
            userService.setFriend(userId, friendId);
        } else {
            userService.deleteFriend(userId, friendId);
        }
    }


}
