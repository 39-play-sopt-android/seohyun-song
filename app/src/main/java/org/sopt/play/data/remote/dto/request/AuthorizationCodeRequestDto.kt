package org.sopt.play.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationCodeRequestDto(
    @SerialName("part") val part: String,
)
