package com.technokratos.util.mapper;

import com.technokratos.dto.StarterUserInfoForJwt;
import com.technokratos.dto.UserInfoForJwt;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.event.UserCreatedEvent;
import com.technokratos.event.UserDeletedEvent;
import com.technokratos.event.UserUpdatedEvent;
import com.technokratos.model.UserEntity;
import com.technokratos.model.UserPrincipal;
import com.technokratos.tables.pojos.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserInfoForJwt toJwtUserInfo(UserEntity userEntity);

    UserInfoForJwt toJwtUserInfo(UserResponse user);

    UserInfoForJwt toJwtUserInfo(UserPrincipal userPrincipal);

    UserEntity accountToUserEntity(Account account);

    StarterUserInfoForJwt toStarterUserInfoJwt(UserInfoForJwt userInfoForJwt);

    StarterUserInfoForJwt toStarterUserInfoJwt(UserResponse user);


    UserEntity userRegistrationRequestToUserEntity(UserRegistrationRequest userRegistrationRequest);

    @Mapping(target = "avatarUrl")
    UserResponse toUserResponse(Account account,
                                String avatarUrl);

    UserResponse toUserResponse(Account account);

    @Mapping(target = "isFollowedByUser")
    @Mapping(target = "isFollowingByUser")
    @Mapping(target = "isFriends")
    @Mapping(target = "avatarUrl")
    UserProfileResponse toUserProfileResponse(Account account,
                                              boolean isFollowedByUser,
                                              boolean isFollowingByUser,
                                              boolean isFriends,
                                              String avatarUrl);

    List<UserCompactResponse> toUserCompactResponse(List<Account> followersByUserId);

    UserCreatedEvent toUserCreatedEvent(Account account);

    UserUpdatedEvent toUserUpdatedEvent(Account account);

    UserDeletedEvent toUserDeletedEvent(UUID userId);
}
