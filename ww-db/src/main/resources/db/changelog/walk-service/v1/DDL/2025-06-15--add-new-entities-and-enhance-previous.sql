-- liquibase formatted sql

-- changeset Mi:1749993100461-4
CREATE TABLE user_walk_visibility_settings
(
    user_id         UUID         NOT NULL,
    walk_visibility VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_walk_visibility_settings PRIMARY KEY (user_id)
);

-- changeset Mi:1749993100461-5
CREATE TABLE walk_invitations
(
    id             UUID                        NOT NULL,
    walk_id        UUID                        NOT NULL,
    participant_id UUID                        NOT NULL,
    token          UUID                        NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expires_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    accepted       BOOLEAN                     NOT NULL,
    CONSTRAINT pk_walk_invitations PRIMARY KEY (id)
);

-- changeset Mi:1749993100461-6
ALTER TABLE walks
    ADD route GEOMETRY(LineString, 4326);
ALTER TABLE walks
    ADD start_point GEOMETRY(Point, 4326);

-- changeset Mi:1749993100461-8
ALTER TABLE walk_invitations
    ADD CONSTRAINT uc_walk_invitations_token UNIQUE (token);

-- changeset Mi:1749993100461-9
ALTER TABLE walk_invitations
    ADD CONSTRAINT FK_WALK_INVITATIONS_ON_WALK FOREIGN KEY (walk_id) REFERENCES walks (walk_id);

-- changeset Mi:1749993100461-1
ALTER TABLE walks
    ALTER COLUMN created_at SET NOT NULL;

-- changeset Mi:1749993100461-2
ALTER TABLE walks
    ALTER COLUMN name SET NOT NULL;

-- changeset Mi:1749993100461-3
ALTER TABLE walks
    ALTER COLUMN user_id SET NOT NULL;

