package com.technokratos.util.mapper;

import com.technokratos.dto.request.security.*;
import com.technokratos.dto.request.user.UserProfileUpdateRequest;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.model.UserEntity;
import com.technokratos.tables.pojos.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponse(UserEntity userEntity);

    List<UserResponse> toResponse(List<Account> users);

    UserForJwtTokenRequest toJwtUserInfo(UserEntity userEntity);

    UserForJwtTokenRequest toJwtUserInfo(Account account);

    UserEntity accountToUserEntity(Account account);

    @Mapping(target = "userId", ignore = true)
    UserEntity userRegistrationRequestToUserEntity(UserRegistrationRequest userRegistrationRequest);

    @Mapping(target = "userId", ignore = true)
    UserEntity userProfileUpdateRequestToUserEntity(UserProfileUpdateRequest userProfileUpdateRequest);

    @Mapping(target = "userId", ignore = true)
    UserEntity adminUserUpdateRequestToUserEntity(AdminUserUpdateRequest adminUserUpdateRequest);

    @Mapping(target = "userId", ignore = true)
    UserEntity userLoginRequestToUserEntity(UserLoginRequest userLoginRequest);

    @Mapping(target = "userId", ignore = true)
    UserEntity passwordChangeRequestToUserEntity(PasswordChangeRequest passwordChangeReq);

    UserResponse toUserResponse(Account account);

    UserProfileResponse toUserProfileResponse(Account account);

    List<UserProfileResponse> toUserProfileResponse(List<Account> accounts);
}
