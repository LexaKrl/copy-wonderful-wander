package com.technokratos.repository;

import com.technokratos.Tables;
import com.technokratos.dto.request.UserRegistrationRequest;
import com.technokratos.model.UserEntity;
import com.technokratos.tables.pojos.Account;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    public Optional<Account> findByUsername(String username) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USERNAME.eq(username))
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }


    public void save(UserRegistrationRequest userDto) {
        dsl
                .insertInto(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.USER_ID, userDto.id())
                .set(Tables.ACCOUNT.USERNAME, userDto.username())
                .set(Tables.ACCOUNT.PASSWORD, userDto.password())
                .set(Tables.ACCOUNT.FIRSTNAME, userDto.firstname())
                .set(Tables.ACCOUNT.LASTNAME, userDto.lastname())
                .set(Tables.ACCOUNT.EMAIL, userDto.email())
                .execute();
    }

    public List<Account> findAll() {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .fetch()
                .map(record -> record.into(Account.class));
    }
}
