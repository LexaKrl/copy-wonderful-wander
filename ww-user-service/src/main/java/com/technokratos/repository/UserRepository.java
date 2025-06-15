package com.technokratos.repository;

import com.technokratos.Tables;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.enums.PhotoVisibility;
import com.technokratos.enums.WalkVisibility;
import com.technokratos.model.UserEntity;
import com.technokratos.tables.pojos.Account;
import io.micrometer.common.KeyValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final DSLContext dsl;

    public Optional<Account> findById(UUID userId) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USER_ID.eq(userId))
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public Optional<Account> findByUsername(String username) {
        log.info("UserRepository: findByUsername() doing...");
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USERNAME.eq(username))
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public Optional<Object> findByEmail(String email) {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.EMAIL.eq(email))
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public List<Account> getFriendsByUserId(UUID userId, int limit, int offset) {
        val ur1 = Tables.USER_RELATIONSHIPS.as("ur1");
        val ur2 = Tables.USER_RELATIONSHIPS.as("ur2");
        return dsl.select(Tables.ACCOUNT.fields())
                .from(Tables.ACCOUNT)
                .innerJoin(dsl.select(
                                ur1.TARGET_USER_ID.as("friend_id"),
                                DSL.greatest(ur1.CREATED_AT, ur2.CREATED_AT).as("friendship_date")
                        )
                        .from(ur1)
                        .innerJoin(ur2)
                        .on(ur1.TARGET_USER_ID.eq(ur2.USER_ID))
                        .and(ur2.TARGET_USER_ID.eq(ur1.USER_ID))
                        .where(ur1.USER_ID.eq(userId))
                        .asTable("friends"))
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("friends", "friend_id"), UUID.class)))
                .orderBy(DSL.field(DSL.name("friends", "friendship_date")).asc())
                .limit(limit)
                .offset(offset)
                .fetch()
                .into(Account.class);
    }

    public List<Account> getFollowersByUserId(UUID userId, int limit, int offset) {
        val ur = Tables.USER_RELATIONSHIPS.as("ur");
        return dsl
                .select(Tables.ACCOUNT.fields())
                .from(Tables.ACCOUNT)
                .innerJoin(
                        dsl.select(ur.USER_ID, ur.CREATED_AT)
                                .from(ur)
                                .where(ur.TARGET_USER_ID.eq(userId))
                                .asTable("follows")
                )
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("follows", "user_id"), UUID.class)))
                .orderBy(DSL.field(DSL.name("follows", "created_at")).asc())
                .limit(limit)
                .offset(offset)
                .fetch()
                .into(Account.class);
    }

    public List<Account> getFollowingByUserId(UUID userId, int limit, int offset) {
        val ur = Tables.USER_RELATIONSHIPS.as("ur");
        return dsl
                .select(Tables.ACCOUNT.fields())
                .from(Tables.ACCOUNT)
                .innerJoin(
                        dsl.select(ur.TARGET_USER_ID, ur.CREATED_AT
                                )
                                .from(ur)
                                .where(ur.USER_ID.eq(userId))
                                .asTable("followings")
                )
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("followings", "target_user_id"), UUID.class)))
                .orderBy(DSL.field(DSL.name("followings", "created_at")).asc())
                .limit(limit)
                .offset(offset)
                .fetch()
                .into(Account.class);
    }


    public void follow(UUID userId, UUID targetUserId) {
        dsl
                .insertInto(Tables.USER_RELATIONSHIPS)
                .set(Tables.USER_RELATIONSHIPS.USER_ID, userId)
                .set(Tables.USER_RELATIONSHIPS.TARGET_USER_ID, targetUserId)
                .execute();
    }

    public void unfollow(UUID userId, UUID targetUserId) {
        dsl
                .deleteFrom(Tables.USER_RELATIONSHIPS)
                .where(Tables.USER_RELATIONSHIPS.USER_ID.eq(userId)
                        .and(Tables.USER_RELATIONSHIPS.TARGET_USER_ID.eq(targetUserId)))
                .execute();
    }

    public void save(UserEntity userEntity) {
        dsl
                .insertInto(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.USER_ID, userEntity.getUserId())
                .set(Tables.ACCOUNT.USERNAME, userEntity.getUsername())
                .set(Tables.ACCOUNT.PASSWORD, userEntity.getPassword())
                .set(Tables.ACCOUNT.FIRSTNAME, userEntity.getFirstname())
                .set(Tables.ACCOUNT.LASTNAME, userEntity.getLastname())
                .set(Tables.ACCOUNT.EMAIL, userEntity.getEmail())
                .execute();
    }

    public List<Account> findAll() {
        return dsl
                .selectFrom(Tables.ACCOUNT)
                .fetch()
                .map(record -> record.into(Account.class));
    }

    public Optional<Account> update(UUID userId, UserRequest userRequest) {
        return dsl
                .update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.EMAIL, userRequest.email())
                .set(Tables.ACCOUNT.FIRSTNAME, userRequest.firstname())
                .set(Tables.ACCOUNT.LASTNAME, userRequest.lastname())
                .set(Tables.ACCOUNT.BIO, userRequest.bio())
                .set(Tables.ACCOUNT.MY_PHOTO_VISIBILITY, PhotoVisibility.valueOf(userRequest.myPhotoVisibility().name()))
                .set(Tables.ACCOUNT.SAVED_PHOTO_VISIBILITY, PhotoVisibility.valueOf(userRequest.savedPhotoVisibility().name()))
                .set(Tables.ACCOUNT.WALK_VISIBILITY, WalkVisibility.valueOf(userRequest.walkVisibility().name()))
                .where(Tables.ACCOUNT.USER_ID.eq(userId))
                .returning()
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public void delete(UUID userId) {
        dsl
                .delete(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.USER_ID.eq(userId))
                .execute();
    }

    public boolean existsById(UUID userId) {
        return dsl.fetchExists(
                dsl
                        .selectFrom(Tables.ACCOUNT)
                        .where(Tables.ACCOUNT.USER_ID.eq(userId))
        );
    }

    public boolean existsFollow(UUID userId, UUID targetUserId) {
        return dsl.fetchExists(
                dsl
                        .selectFrom(Tables.USER_RELATIONSHIPS)
                        .where(Tables.USER_RELATIONSHIPS.USER_ID.eq(userId)
                                .and(Tables.USER_RELATIONSHIPS.TARGET_USER_ID.eq(targetUserId)))
        );
    }

    public void changePassword(UUID userId, String newPassword) {
        dsl
                .update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.PASSWORD, newPassword)
                .where(Tables.ACCOUNT.USER_ID.eq(userId))
                .returning()
                .fetchOptional()
                .map(record -> record.into(Account.class));
    }

    public void saveFilenameByUserId(UUID userId, String filename) {
        dsl
                .update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.AVATAR_FILENAME, filename)
                .where(Tables.ACCOUNT.USER_ID.eq(userId))
                .execute();
    }

    public Integer getCountFollowersByUserId(UUID userId) {
        val ur = Tables.USER_RELATIONSHIPS.as("ur");
        return dsl
                .selectCount()
                .from(Tables.ACCOUNT)
                .innerJoin(
                        dsl.select(ur.USER_ID, ur.CREATED_AT)
                                .from(ur)
                                .where(ur.TARGET_USER_ID.eq(userId))
                                .asTable("follows")
                )
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("follows", "user_id"), UUID.class)))
                .fetchOneInto(Integer.class);
    }

    public Integer getCountFriendsByUserId(UUID userId) {
        val ur1 = Tables.USER_RELATIONSHIPS.as("ur1");
        val ur2 = Tables.USER_RELATIONSHIPS.as("ur2");
        return dsl.selectCount()
                .from(Tables.ACCOUNT)
                .innerJoin(dsl.select(
                                ur1.TARGET_USER_ID.as("friend_id"),
                                DSL.greatest(ur1.CREATED_AT, ur2.CREATED_AT).as("friendship_date")
                        )
                        .from(ur1)
                        .innerJoin(ur2)
                        .on(ur1.TARGET_USER_ID.eq(ur2.USER_ID))
                        .and(ur2.TARGET_USER_ID.eq(ur1.USER_ID))
                        .where(ur1.USER_ID.eq(userId))
                        .asTable("friends"))
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("friends", "friend_id"), UUID.class)))
                .fetchOneInto(Integer.class);
    }

    public Integer getCountFollowingByUserId(UUID userId) {
        val ur = Tables.USER_RELATIONSHIPS.as("ur");
        return dsl
                .selectCount()
                .from(Tables.ACCOUNT)
                .innerJoin(
                        dsl.select(ur.TARGET_USER_ID, ur.CREATED_AT
                                )
                                .from(ur)
                                .where(ur.USER_ID.eq(userId))
                                .asTable("followings")
                )
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("followings", "target_user_id"), UUID.class)))
                .fetchOneInto(Integer.class);
    }

    public boolean isFriends(UUID userId, UUID targetUserId) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(Tables.USER_RELATIONSHIPS)
                        .where(Tables.USER_RELATIONSHIPS.USER_ID.eq(userId)
                                .and(Tables.USER_RELATIONSHIPS.TARGET_USER_ID.eq(targetUserId)))
                        .andExists(
                                dsl.selectOne()
                                        .from(Tables.USER_RELATIONSHIPS)
                                        .where(Tables.USER_RELATIONSHIPS.USER_ID.eq(targetUserId)
                                                .and(Tables.USER_RELATIONSHIPS.TARGET_USER_ID.eq(userId)))
                        )
        );
    }

    public List<Account> getFriendsForPostByUserId(UUID userId) {

        val ur1 = Tables.USER_RELATIONSHIPS.as("ur1");
        val ur2 = Tables.USER_RELATIONSHIPS.as("ur2");

        return dsl.select(Tables.ACCOUNT.fields())
                .from(Tables.ACCOUNT)
                .innerJoin(
                        dsl.select(
                                        ur1.TARGET_USER_ID.as("friend_id"),
                                        DSL.greatest(ur1.CREATED_AT, ur2.CREATED_AT).as("friendship_date")
                                )
                                .from(ur1)
                                .innerJoin(ur2)
                                .on(ur1.TARGET_USER_ID.eq(ur2.USER_ID))
                                .and(ur2.TARGET_USER_ID.eq(ur1.USER_ID))
                                .where(ur1.USER_ID.eq(userId))
                                .asTable("friends")
                )
                .on(Tables.ACCOUNT.USER_ID.eq(DSL.field(DSL.name("friends", "friend_id"), UUID.class)))
                .orderBy(DSL.field(DSL.name("friends", "friendship_date")).asc())
                .fetch()
                .into(Account.class);

    }
}
