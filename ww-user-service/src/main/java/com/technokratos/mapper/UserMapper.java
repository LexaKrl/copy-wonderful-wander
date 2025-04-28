package com.technokratos.mapper;

import com.technokratos.dto.request.*;
import com.technokratos.dto.response.UserLoginResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserResponse toResponse(UserEntity userEntity);

    UserForJwtTokenRequest toJwtUserInfo(UserEntity userEntity);

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
