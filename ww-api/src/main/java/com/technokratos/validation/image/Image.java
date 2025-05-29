package com.technokratos.validation.image;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {
    String message() default "Unsupported type";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
