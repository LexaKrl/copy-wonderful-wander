package com.technokratos.repository;

import com.technokratos.Tables;
import com.technokratos.tables.pojos.Account;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dsl;

    public Optional<Account> findUserById(UUID userId) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USER_ID.eq(userId))
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public Optional<Account> findUserByUsername(String username) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USERNAME.eq(username))
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public List<Account> getFriendsByUserId(UUID userId, UUID targetUserId) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USER_ID.in(
                        dsl
                                .select(DSL.when(Tables.USER_RELATIONSHIPS.as("ur").USER_ID.eq(userId),
                                                Tables.USER_RELATIONSHIPS.as("ur").TARGET_USER_ID)
                                        .otherwise(Tables.USER_RELATIONSHIPS.as("ur").USER_ID))
                                .from(Tables.USER_RELATIONSHIPS.as("ur"))
                                .where(DSL.exists(dsl
                                        .select(DSL.one())
                                        .from(Tables.USER_RELATIONSHIPS.as("ur2"))
                                        .where(Tables.USER_RELATIONSHIPS.as("ur").USER_ID.eq(
                                                Tables.USER_RELATIONSHIPS.as("ur2").TARGET_USER_ID))
                                        .and(Tables.USER_RELATIONSHIPS.as("ur").TARGET_USER_ID.eq(
                                                Tables.USER_RELATIONSHIPS.as("ur2").USER_ID))
                                ))
                                .and(Tables.USER_RELATIONSHIPS.as("ur").USER_ID.eq(userId)
                                        .or(Tables.USER_RELATIONSHIPS.as("ur").TARGET_USER_ID.eq(userId)))
                ))
                .fetch()
                .into(Account.class);
    }

    public List<Account> getFollowersByUserId(UUID userId) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USER_ID.in(
                        dsl
                                .select(Tables.USER_RELATIONSHIPS.as("ur").USER_ID)
                                .from(Tables.USER_RELATIONSHIPS.as("ur"))
                                .where(Tables.USER_RELATIONSHIPS.as("ur").TARGET_USER_ID.eq(userId))
                ))
                .fetch()
                .into(Account.class);
    }

    public List<Account> getFollowingByUserId(UUID userId) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USER_ID.in(
                        dsl
                                .select(Tables.USER_RELATIONSHIPS.as("ur").TARGET_USER_ID)
                                .from(Tables.USER_RELATIONSHIPS.as("ur"))
                                .where(Tables.USER_RELATIONSHIPS.as("ur").USER_ID.eq(userId))
                ))
                .fetch()
                .into(Account.class);
    }

    public void subscribe(UUID userId, UUID targetUserId) {
        dsl
                .insertInto(Tables.USER_RELATIONSHIPS)
                .set(Tables.USER_RELATIONSHIPS.USER_ID, userId)
                .set(Tables.USER_RELATIONSHIPS.TARGET_USER_ID, targetUserId)
                .execute();
    }

    public void unsubscribe(UUID userId, UUID targetUserId) {
        dsl
                .deleteFrom(Tables.USER_RELATIONSHIPS)
                .where(Tables.USER_RELATIONSHIPS.USER_ID.eq(userId).and(
                        Tables.USER_RELATIONSHIPS.TARGET_USER_ID.eq(targetUserId)))
                .execute();
    }
}
