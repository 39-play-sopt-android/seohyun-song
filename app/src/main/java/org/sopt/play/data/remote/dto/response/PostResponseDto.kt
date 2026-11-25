package org.sopt.play.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.play.core.extensions.toDisplayDate
import org.sopt.play.presentation.posts.model.PostCardUiModel

@Serializable
data class PostResponseDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: String,
    @SerialName("createdAt") val createdAt: String,
)

fun PostResponseDto.toUiModel() = PostCardUiModel(
    id = id,
    title = title,
    content = content,
    author = author,
    createdAt = createdAt.toDisplayDate(),
)
