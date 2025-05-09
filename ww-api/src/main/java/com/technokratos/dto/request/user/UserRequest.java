package com.technokratos.dto.request.user;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRequest(
        String email,
        String firstname,
        String lastname,
        String bio,
        PhotoVisibility photoVisibility,
        WalkVisibility walkVisibility) {
}