package com.technokratos.enums.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Уровень видимости своих прогулок другим пользователям")
public enum WalkVisibility {
    @Schema(description = "Видно всем")
    PUBLIC,
    @Schema(description = "Видно только друзьям")
    FRIENDS_ONLY,
    @Schema(description = "Видно только участникам прогулки")
    REMEMBER_ONLY,
    @Schema(description = "Видно только мне")
    PRIVATE
}
