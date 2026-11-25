package org.sopt.play.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSliceResponseDto(
    @SerialName("posts") val posts: List<PostResponseDto>,
    @SerialName("nextCursor") val nextCursor: Long? = null,
    @SerialName("hasNext") val hasNext: Boolean,
)
