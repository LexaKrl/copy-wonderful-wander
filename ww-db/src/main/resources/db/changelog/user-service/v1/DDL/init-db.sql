CREATE TYPE user_role AS ENUM ('ROLE_USER', 'ROLE_ADMIN');

CREATE TABLE user_info
(
    user_id    UUID                NOT NULL,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR NOT NULL,
    name       VARCHAR(50),
    lastname   VARCHAR(50),
    email      VARCHAR(255) UNIQUE NOT NULL,
    bio        VARCHAR(255),
    role       user_role DEFAULT 'ROLE_USER',
    avatar_url VARCHAR(2048),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    -------------------------
    CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);

CREATE TYPE photo_visibility AS ENUM ('public', 'friends_only', 'private');
CREATE TYPE walk_visibility AS ENUM ('public', 'friends_only', 'remember_only', 'private');


CREATE TABLE user_settings
(
    user_id          UUID NOT NULL,
    photo_visibility photo_visibility DEFAULT 'public',
    walk_visibility  walk_visibility  DEFAULT 'remember_only',
    ----------------------------------------------------------
    CONSTRAINT user_settings_id_pk PRIMARY KEY (user_id),
    CONSTRAINT user_settings_id_fk FOREIGN KEY (user_id) REFERENCES user_info (user_id) ON DELETE CASCADE
);


CREATE TABLE user_relationships
(
    relationship_id  UUID NOT NULL,
    user_id          UUID NOT NULL,
    target_user_id   UUID NOT NULL,
    is_subscribed    BOOLEAN DEFAULT FALSE,
    is_followed_back BOOLEAN DEFAULT FALSE,
    ---------------------------------------
    CONSTRAINT user_relationships_id_fk FOREIGN KEY (user_id) REFERENCES user_info (user_id) ON DELETE CASCADE,
    CONSTRAINT user_relationships_target_fk FOREIGN KEY (target_user_id) REFERENCES user_info (user_id) ON DELETE CASCADE,
    CONSTRAINT user_relationship_uq UNIQUE (user_id, target_user_id),
    CONSTRAINT user_relationship_chk CHECK (user_id != target_user_id)
);

CREATE TABLE user_statistics
(
    user_id         UUID NOT NULL,
    followers_count INT DEFAULT 0,
    following_count INT DEFAULT 0,
    friends_count   INT DEFAULT 0,
    --------------------------------
    CONSTRAINT user_relationships_id_pk PRIMARY KEY (user_id),
    CONSTRAINT user_relationships_id_fk FOREIGN KEY (user_id) REFERENCES user_info (user_id) ON DELETE CASCADE
);
