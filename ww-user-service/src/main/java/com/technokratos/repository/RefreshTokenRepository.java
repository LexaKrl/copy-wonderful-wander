package com.technokratos.repository;


import com.technokratos.Tables;
import com.technokratos.tables.pojos.Account;
import com.technokratos.tables.pojos.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.technokratos.tables.RefreshToken.REFRESH_TOKEN;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final DSLContext dsl;

    public void save(RefreshToken refreshToken) {
        dsl.insertInto(REFRESH_TOKEN)
                .set(REFRESH_TOKEN.REFRESH_TOKEN_ID, refreshToken.getRefreshTokenId())
                .set(REFRESH_TOKEN.TOKEN, refreshToken.getToken())
                .set(REFRESH_TOKEN.USER_ID, refreshToken.getUserId())
                .set(REFRESH_TOKEN.EXPIRY_DATE, refreshToken.getExpiryDate())
                .execute();
    }

    public Optional<RefreshToken> findByToken(String token) {
        return dsl
                .selectFrom(REFRESH_TOKEN)
                .where(REFRESH_TOKEN.TOKEN.eq(token))
                .fetchOptional()
                .map(record -> record.into(RefreshToken.class));
    }

    public void deleteByToken(String token) {
        dsl.deleteFrom(REFRESH_TOKEN)
                .where(REFRESH_TOKEN.TOKEN.eq(token))
                .execute();
    }

    public boolean existsByToken(String token) {
        return dsl.fetchExists(
                dsl.selectFrom(REFRESH_TOKEN)
                        .where(REFRESH_TOKEN.TOKEN.eq(token))
        );
    }

    public void changeToken(UUID tokenId, String newToken) {
        dsl.update(REFRESH_TOKEN)
                .set(REFRESH_TOKEN.TOKEN, newToken)
                .where(REFRESH_TOKEN.REFRESH_TOKEN_ID.eq(tokenId))
                .execute();
    }
}