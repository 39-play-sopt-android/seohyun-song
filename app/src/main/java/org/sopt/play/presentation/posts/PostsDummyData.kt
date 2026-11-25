package org.sopt.play.presentation.posts

import org.sopt.play.presentation.posts.model.PostCardUiModel

object PostsDummyData {
    val posts: List<PostCardUiModel> = List(5) { index ->
        PostCardUiModel(
            id = index.toLong(),
            title = "더미 게시글띠니 ${index + 1}",
            content = "더미 본문띠니 ".repeat(index + 1),
            author = "흠나흠나",
            createdAt = "2026.11.26",
        )
    }
}
