-- liquibase formatted sql

-- changeset Mi:1750259749907-1
CREATE TABLE user_data
(
    user_id   UUID NOT NULL,
    email     VARCHAR(255),
    fcm_token VARCHAR(255),
    CONSTRAINT pk_user_data PRIMARY KEY (user_id)
);