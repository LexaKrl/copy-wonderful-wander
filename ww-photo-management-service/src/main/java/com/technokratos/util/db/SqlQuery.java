package com.technokratos.util.db;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlQuery {
    public static final class Photo {
        //language=sql
        public static final String SAVE =
                """
                        INSERT INTO file_stored_metadata (file_id, owner_id, filename, extension, size, upload_date)
                        VALUES (:%s, :%s, :%s, :%s::file_extension, :%s, :%s)
                        """
                        .formatted(
                                SqlParameters.Photo.FILE_ID,
                                SqlParameters.Photo.OWNER_ID,
                                SqlParameters.Photo.FILENAME,
                                SqlParameters.Photo.EXTENSION,
                                SqlParameters.Photo.SIZE,
                                SqlParameters.Photo.UPLOAD_DATE
                        );
    }
}