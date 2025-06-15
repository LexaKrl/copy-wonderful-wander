package com.technokratos.validation.image;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".jpg", ".jpeg", ".png", ".gif", ".webp"
    );

    private static final Tika tika = new Tika();

    @Override
    public void initialize(Image constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        try {
            return !value.isEmpty()
                    && hasAllowedExtension(value.getOriginalFilename())
                    && hasAllowedMimeTypeFromClient(value.getContentType())
                    && hasAllowedRealMimeType(value);
        } catch (IOException e) {
            return false;
        }
    }

    private boolean hasAllowedExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }

        int dotIndex = filename.lastIndexOf('.');

        String extension = filename.substring(dotIndex).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    private boolean hasAllowedMimeTypeFromClient(String clientMimeType) {
        return clientMimeType != null && ALLOWED_MIME_TYPES.contains(clientMimeType);
    }

    private boolean hasAllowedRealMimeType(MultipartFile file) throws IOException {
        String realMimeType;
        try (InputStream is = file.getInputStream()) {
            realMimeType = tika.detect(is, file.getOriginalFilename());
        }
        return realMimeType != null && ALLOWED_MIME_TYPES.contains(realMimeType);
    }
}
