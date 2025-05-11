package com.technokratos.dto.request.user;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "First name is required")
        @Length(min = 3, max = 64, message = "Firstname length should be between 3 to 64 characters")
        String firstname,
        @NotBlank(message = "Lastname is required")
        @Length(min = 3, max = 64, message = "Lastname length should be between 3 to 64 characters")
        String lastname,
        @Length(max = 254, message = "Bio cannot exceed 254 characters")
        String bio,
        @NotNull(message = "Photo visibility is required")
        PhotoVisibility photoVisibility,
        @NotNull(message = "Walk visibility is required")
        WalkVisibility walkVisibility) {
}