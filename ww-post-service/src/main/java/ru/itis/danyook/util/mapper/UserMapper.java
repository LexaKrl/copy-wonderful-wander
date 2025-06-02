package ru.itis.danyook.util.mapper;

import com.technokratos.dto.response.user.UserCompactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import ru.itis.danyook.model.CachedUserEntity;
import ru.itis.danyook.model.EmbeddedUser;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    EmbeddedUser toEmbeddedUserEntity(CachedUserEntity cachedUserEntity);
    UserCompactResponse toUserCompactResponse(EmbeddedUser user);

}