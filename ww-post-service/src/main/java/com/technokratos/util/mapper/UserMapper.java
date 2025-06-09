package com.technokratos.util.mapper;

import com.technokratos.dto.response.user.UserCompactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import com.technokratos.model.CachedUserEntity;
import com.technokratos.model.EmbeddedUser;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    EmbeddedUser toEmbeddedUserEntity(CachedUserEntity cachedUserEntity);
    UserCompactResponse toUserCompactResponse(EmbeddedUser user);

}