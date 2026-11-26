package org.sopt.play.presentation.posts.model

import org.sopt.play.core.extensions.toDisplayDate
import org.sopt.play.data.remote.dto.response.PostResponseDto

data class PostCardUiModel(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
)

fun PostResponseDto.toUiModel() = PostCardUiModel(
    id = id,
    title = title,
    content = content,
    author = author,
    createdAt = createdAt.toDisplayDate(),
)
