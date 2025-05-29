package com.technokratos.util.db;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlParameters {
    public static final class Photo {
        public static final String FILE_ID = "file_id";
        public static final String OWNER_ID = "owner_id";
        public static final String FILENAME = "filename";
        public static final String EXTENSION = "extension";
        public static final String SIZE = "size";
        public static final String UPLOAD_DATE = "upload_date";
    }
}
