package com.technokratos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequest userRequest);

    UserResponse toResponse(UserEntity userEntity);

}
