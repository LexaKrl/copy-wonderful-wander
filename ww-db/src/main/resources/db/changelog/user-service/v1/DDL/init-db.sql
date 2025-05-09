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
    avatar_url       VARCHAR(2048),
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
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE SET NULL,
    CONSTRAINT fk_target_user FOREIGN KEY (target_user_id) REFERENCES account (user_id) ON DELETE SET NULL
);