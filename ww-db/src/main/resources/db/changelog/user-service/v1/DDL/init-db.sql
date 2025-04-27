CREATE TYPE photo_visibility AS ENUM ('PUBLIC', 'FRIENDS_ONLY', 'PRIVATE');
CREATE TYPE walk_visibility AS ENUM ('PUBLIC', 'FRIENDS_ONLY', 'REMEMBER_ONLY', 'PRIVATE');

CREATE TABLE account
(
    user_id          UUID                NOT NULL,
    username         VARCHAR(50) UNIQUE  NOT NULL,
    email            VARCHAR(255) UNIQUE NOT NULL,
    bio              VARCHAR(255),
    avatar_url       VARCHAR(2048),
    followers_count  INT              DEFAULT 0,
    following_count  INT              DEFAULT 0,
    friends_count    INT              DEFAULT 0,
    photo_visibility photo_visibility DEFAULT 'PUBLIC',
    walk_visibility  walk_visibility  DEFAULT 'REMEMBER_ONLY',
    ------------------------------
    CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);


CREATE SEQUENCE user_relationships_sequence
    START 100000
    INCREMENT BY 1
    CACHE 50;

CREATE TABLE user_relationships
(
    relationship_id BIGINT NOT NULL DEFAULT NEXTVAL('user_relationships_sequence'),
    user_id         UUID   NOT NULL,
    target_user_id  UUID   NOT NULL,
    ---------------------------------------
    CONSTRAINT user_relationships_id_fk FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE SET NULL,
    CONSTRAINT user_relationships_target_fk FOREIGN KEY (target_user_id) REFERENCES account (user_id) ON DELETE SET NULL,
    CONSTRAINT user_relationship_uq UNIQUE (user_id, target_user_id),
    CONSTRAINT user_relationship_chk CHECK (user_id != target_user_id)
);
