package com.technokratos.util.s3;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class FileNameBuilder {
    public static String buildFileName(UUID fileId, String filename, String fileType) {
        return "%s/%s".formatted(fileType, createUniqueName(filename, fileId));
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private static String createUniqueName(String fileName, UUID uniqueFilename) {
        return uniqueFilename.toString().concat(".").concat(getFileExtension(fileName));
    }
}
