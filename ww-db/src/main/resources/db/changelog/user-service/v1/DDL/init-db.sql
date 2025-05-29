CREATE TYPE photo_visibility AS ENUM ('PUBLIC', 'FRIENDS_ONLY', 'PRIVATE');
CREATE TYPE walk_visibility AS ENUM ('PUBLIC', 'FRIENDS_ONLY', 'REMEMBER_ONLY', 'PRIVATE');

CREATE TABLE account
(
    user_id          UUID                NOT NULL,
    username         VARCHAR(50) UNIQUE  NOT NULL,
    email            VARCHAR(255) UNIQUE NOT NULL,
    firstname        VARCHAR(50),
    lastname         VARCHAR(50),
    password         VARCHAR             NOT NULL,
    bio              VARCHAR(255),
    role             VARCHAR(20)      DEFAULT 'ROLE_USER',
    avatar_filename  VARCHAR(2048),
    created_at       TIMESTAMP        DEFAULT NOW(),
    updated_at       TIMESTAMP,
    followers_count  INT              DEFAULT 0,
    following_count  INT              DEFAULT 0,
    friends_count    INT              DEFAULT 0,
    photo_visibility photo_visibility DEFAULT 'PUBLIC',
    walk_visibility  walk_visibility  DEFAULT 'REMEMBER_ONLY',
    ----------------------------------------------------------
    CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);

CREATE TABLE user_relationships
(
    user_id        UUID NOT NULL,
    target_user_id UUID NOT NULL,
    created_at     TIMESTAMP DEFAULT NOW(),
    ---------------------------------------
    CONSTRAINT pk_user_relationship PRIMARY KEY (user_id, target_user_id),
    CONSTRAINT chk_not_self CHECK (user_id != target_user_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_target_user FOREIGN KEY (target_user_id) REFERENCES account (user_id) ON DELETE CASCADE
);

CREATE TABLE refresh_token
(
    refresh_token_id UUID                NOT NULL,
    token            VARCHAR(255) UNIQUE NOT NULL,
    user_id          UUID                NOT NULL,
    expiry_date      TIMESTAMP           NOT NULL,
    ----------------------------------------------------------
    CONSTRAINT refresh_token_id_pk PRIMARY KEY (refresh_token_id),
    CONSTRAINT refresh_token_user_id_fk FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE SET NULL
);

CREATE OR REPLACE FUNCTION update_user_stats()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        -- Увеличиваем подписки и подписчики
        UPDATE account SET following_count = following_count + 1 WHERE user_id = NEW.user_id;
        UPDATE account SET followers_count = followers_count + 1 WHERE user_id = NEW.target_user_id;

        -- Проверяем, есть ли обратная подписка → это дружба
        PERFORM 1
        FROM user_relationships
        WHERE user_id = NEW.target_user_id
          AND target_user_id = NEW.user_id;

        IF FOUND THEN
            -- Оба пользователя подписаны друг на друга → это дружба
            UPDATE account
            SET friends_count = friends_count + 1
            WHERE user_id IN (NEW.user_id, NEW.target_user_id);
        END IF;

    ELSIF TG_OP = 'DELETE' THEN
        -- Получаем ID пользователей до удаления записи
        PERFORM 1
        FROM user_relationships
        WHERE user_id = OLD.target_user_id
          AND target_user_id = OLD.user_id;

        -- Сохраняем флаг, были ли друзьями
        IF FOUND THEN
            -- Это была взаимная подписка → значит, они были друзьями
            UPDATE account
            SET friends_count = GREATEST(friends_count - 1, 0)
            WHERE user_id IN (OLD.user_id, OLD.target_user_id);
        END IF;

        -- Уменьшаем подписки и подписчики
        UPDATE account
        SET following_count = GREATEST(following_count - 1, 0)
        WHERE user_id = OLD.user_id;

        UPDATE account
        SET followers_count = GREATEST(followers_count - 1, 0)
        WHERE user_id = OLD.target_user_id;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_user_stats
    AFTER INSERT OR DELETE
    ON user_relationships
    FOR EACH ROW
EXECUTE FUNCTION update_user_stats();