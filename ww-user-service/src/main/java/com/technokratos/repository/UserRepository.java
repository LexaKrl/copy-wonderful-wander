package com.technokratos.repository;

import com.technokratos.tables.UserInfo;
import com.technokratos.tables.records.UserInfoRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dslContext;

    public Optional<UserInfoRecord> findUserById(UUID userId) {
        return Optional.ofNullable(dslContext.selectFrom(UserInfo.USER_INFO)
                .where(UserInfo.USER_INFO.USER_ID.eq(userId))
                .fetchOne());
    }
}
