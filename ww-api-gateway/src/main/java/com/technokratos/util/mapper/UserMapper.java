package com.technokratos.util.mapper;


import com.technokratos.dto.enums.StarterUserRole;
import com.technokratos.enums.security.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserRole toUserRole(StarterUserRole userRole);

}