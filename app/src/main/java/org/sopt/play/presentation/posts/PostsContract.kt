package org.sopt.play.presentation.posts

import org.sopt.play.presentation.posts.model.PostCardUiModel

data class PostsUiState(
    val posts: List<PostCardUiModel> = emptyList(),
)
