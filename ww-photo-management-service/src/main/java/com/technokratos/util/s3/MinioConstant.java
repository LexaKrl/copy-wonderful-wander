package com.technokratos.util.s3;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MinioConstant {
    public static final class BucketName {
        public static final String PHOTOS = "ww-photos";
    }

    public static final class FileType {
        public static final String WALKS = "walks";
        public static final String POSTS = "posts";
        public static final String AVATARS = "avatars";
    }
}
