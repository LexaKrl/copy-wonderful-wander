package com.technokratos.util.mapper;

import com.technokratos.dto.request.security.UserForJwtTokenRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.model.UserEntity;
import com.technokratos.model.UserPrincipal;
import com.technokratos.tables.pojos.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserForJwtTokenRequest toJwtUserInfo(UserEntity userEntity);

    UserForJwtTokenRequest toJwtUserInfo(UserResponse user);

    UserForJwtTokenRequest toJwtUserInfo(UserPrincipal userPrincipal);

    UserEntity accountToUserEntity(Account account);

    UserEntity userRegistrationRequestToUserEntity(UserRegistrationRequest userRegistrationRequest);

    UserResponse toUserResponse(Account account);

    @Mapping(target = "isFollowedByUser")
    @Mapping(target = "isFollowingByUser")
    @Mapping(target = "isFriends")
    UserProfileResponse toUserProfileResponse(Account account,
                                              boolean isFollowedByUser,
                                              boolean isFollowingByUser,
                                              boolean isFriends);

    List<UserCompactResponse> toUserCompactResponse(List<Account> followersByUserId);
}
