CREATE TYPE file_extension AS ENUM ('jpg', 'jpeg', 'png', 'gif', 'webp');

CREATE TABLE file_stored_metadata
(
    file_id     UUID         NOT NULL,
    owner_id    UUID         NOT NULL,
    filename    VARCHAR(255) NOT NULL,
    file_type   VARCHAR(50),
    extension   file_extension,
    size        DECIMAL,
    upload_date TIMESTAMP DEFAULT NOW(),
    -----------------------------------
    CONSTRAINT file_id_pk PRIMARY KEY (file_id)
);
