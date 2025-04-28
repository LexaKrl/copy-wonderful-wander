package com.technokratos.mapper;

import com.technokratos.dto.request.*;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserEntity;
import com.technokratos.tables.pojos.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserResponse toResponse(UserEntity userEntity);

    List<UserResponse> toResponse(List<Account> users);

    UserForJwtTokenRequest toJwtUserInfo(UserEntity userEntity);

    UserEntity accountToUserEntity(Account account);

    @Mapping(target = "id", ignore = true)
    UserEntity userRegistrationRequestToUserEntity(UserRegistrationRequest userRegistrationRequest);

    @Mapping(target = "id", ignore = true)
    UserEntity userProfileUpdateRequestToUserEntity(UserProfileUpdateRequest userProfileUpdateRequest);

    @Mapping(target = "id", ignore = true)
    UserEntity adminUserUpdateRequestToUserEntity(AdminUserUpdateRequest adminUserUpdateRequest);

    @Mapping(target = "id", ignore = true)
    UserEntity userLoginRequestToUserEntity(UserLoginRequest userLoginRequest);

    @Mapping(target = "id", ignore = true)
    UserEntity passwordChangeRequestToUserEntity(PasswordChangeRequest passwordChangeReq);
}
