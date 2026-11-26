package org.sopt.play.presentation.posts

import org.sopt.play.presentation.posts.model.PostCardUiModel
import org.sopt.play.presentation.posts.model.PostSheetMode
import org.sopt.play.presentation.posts.model.PostSheetMode.Create
import org.sopt.play.presentation.posts.model.PostSheetMode.Edit

internal const val SEARCH_KEYWORD_MIN_LENGTH = 2
internal const val SEARCH_KEYWORD_MAX_LENGTH = 50

data class PostsUiState(
    val posts: List<PostCardUiModel> = emptyList(),
    val keyword: String = "",
    val isLoading: Boolean = true,
    val sheetMode: PostSheetMode? = null,
    val deleteTargetId: Long? = null,
    val title: String = "",
    val content: String = "",
    val myName: String? = null,
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
) {
    val showEmpty: Boolean
        get() = !isLoading && posts.isEmpty()

    val emptyMessage: String
        get() = if (keyword.isNotEmpty()) "검색 결과가 없습니다." else "아직 작성된 게시글이 없습니다."

    val completeEnabled: Boolean
        get() {
            val isFilled = title.isNotBlank() && content.isNotBlank()

            return when (val mode = sheetMode) {
                null -> false
                Create -> isFilled
                is Edit -> isFilled &&
                        (title.trim() != mode.initialTitle.trim() ||
                                content.trim() != mode.initialContent.trim())
            }
        }

    fun isMine(post: PostCardUiModel): Boolean =
        myName != null && post.author == myName
}

sealed interface PostsIntent {
    data object SearchClick : PostsIntent
    data class PostClick(val postId: Long) : PostsIntent
    data class PostLongClick(val postId: Long) : PostsIntent
    data object AddClick : PostsIntent
    data object CompleteClick : PostsIntent
    data object SheetDismiss : PostsIntent
    data object DeleteClick : PostsIntent
    data object ModalDismiss : PostsIntent
    data object LoadMore : PostsIntent
}

sealed interface PostsSideEffect {
    data class ShowToast(val message: String) : PostsSideEffect
}
