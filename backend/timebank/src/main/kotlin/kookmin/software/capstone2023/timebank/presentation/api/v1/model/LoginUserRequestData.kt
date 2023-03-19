package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import jakarta.validation.constraints.NotBlank
import kookmin.software.capstone2023.timebank.domain.model.SocialPlatformType

sealed class LoginUserRequestData {
    data class SocialLoginUserRequestData(
        @field:NotBlank
        val provider: SocialPlatformType,
        @field:NotBlank
        val accessToken: String,
    ) : LoginUserRequestData()
}
