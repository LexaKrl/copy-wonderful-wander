package com.technokratos.util.mapper;

import com.technokratos.dto.response.UserCompactResponse;
import com.technokratos.dto.response.UserProfileResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.tables.pojos.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponse toUserResponse(Account account);

    UserProfileResponse toUserProfileResponse(Account account);

    UserCompactResponse toUserCompactResponse(Account account);

    default List<UserCompactResponse> toUserCompactResponseList(List<Account> accounts) {
        return accounts.stream().map(this::toUserCompactResponse).toList();
    }
}
