package org.sopt.play.presentation.posts.model

sealed interface PostSheetMode {
    data object Create : PostSheetMode

    data class Edit(
        val postId: Long,
        val initialTitle: String,
        val initialContent: String,
    ) : PostSheetMode
}
