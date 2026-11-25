package org.sopt.play.presentation.posts.model

data class PostCardUiModel(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
)
