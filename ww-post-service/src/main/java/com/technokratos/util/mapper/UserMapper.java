package com.technokratos.util.mapper;

import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserForPostResponse;
import com.technokratos.event.UserCreatedEvent;
import com.technokratos.event.UserUpdatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import com.technokratos.model.CachedUserEntity;
import com.technokratos.model.EmbeddedUser;

import java.util.List;
import java.util.UUID;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    EmbeddedUser toEmbeddedUserEntity(CachedUserEntity cachedUserEntity);

    @Mapping(target = "avatarUrl")
    @Mapping(target = "userId")
    UserCompactResponse toUserCompactResponse(EmbeddedUser user, UUID userId, String avatarUrl);

    @Mapping(target = "userId")
    CachedUserEntity toCachedUserEntity(UserCreatedEvent event, String userId);


    @Mapping(target = "userId")
    CachedUserEntity toCachedUserEntity(UserUpdatedEvent event, String userId);

    CachedUserEntity toCachedUserEntity(UserForPostResponse userForPostResponse);

}