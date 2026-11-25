package org.sopt.play.presentation.posts

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.presentation.posts.model.PostCardUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostsViewModel : ViewModel() {
    val searchState = TextFieldState()
    val titleState = TextFieldState()
    val contentState = TextFieldState()

    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = combine(
        _uiState,
        snapshotFlow { titleState.text.toString() },
        snapshotFlow { contentState.text.toString() },
    ) { state, title, content ->
        state.copy(title = title, content = content)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PostsUiState(),
    )

    private val _sideEffect = Channel<PostsSideEffect>()
    val sideEffect: Flow<PostsSideEffect> = _sideEffect.receiveAsFlow()

    // TODO: API 연동 시 제거
    private val dummyPosts = PostsDummyData.posts.toMutableList()
    private var nextId = dummyPosts.size.toLong()

    init {
        refreshPosts()
    }

    fun onIntent(intent: PostsIntent) {
        when (intent) {
            PostsIntent.SearchClick -> search()
            is PostsIntent.PostClick -> openEditSheet(intent.postId)
            is PostsIntent.PostLongClick -> openDeleteModal(intent.postId)
            PostsIntent.AddClick -> openCreateSheet()
            PostsIntent.CompleteClick -> complete()
            PostsIntent.SheetDismiss -> closeSheet()
            PostsIntent.DeleteClick -> deletePost()
            PostsIntent.ModalDismiss -> closeDeleteModal()
        }
    }

    private fun search() {
        _uiState.update {
            it.copy(keyword = searchState.text.toString().trim())
        }

        refreshPosts()
    }

    // TODO: API 연동 시 GET /api/v1/posts?keyword 호출로 교체
    private fun refreshPosts() {
        val keyword = _uiState.value.keyword
        val posts = dummyPosts
            .filter {
                keyword.isBlank() ||
                        it.title.contains(keyword, ignoreCase = true) ||
                        it.content.contains(keyword, ignoreCase = true)
            }.sortedByDescending { it.id }

        _uiState.update { it.copy(posts = posts, isLoading = false) }
    }

    private fun openCreateSheet() {
        titleState.clearText()
        contentState.clearText()

        _uiState.update {
            it.copy(sheetMode = PostSheetMode.Create)
        }
    }

    private fun openEditSheet(postId: Long) {
        val post = _uiState.value.posts.find { it.id == postId } ?: return

        titleState.setTextAndPlaceCursorAtEnd(post.title)
        contentState.setTextAndPlaceCursorAtEnd(post.content)

        _uiState.update {
            it.copy(sheetMode = PostSheetMode.Edit(post.id, post.title, post.content))
        }
    }

    private fun closeSheet() {
        _uiState.update { it.copy(sheetMode = null) }
    }

    private fun openDeleteModal(postId: Long) {
        _uiState.update { it.copy(deleteTargetId = postId) }
    }

    private fun closeDeleteModal() {
        _uiState.update { it.copy(deleteTargetId = null) }
    }

    // TODO: API 연동 시 POST /api/v1/posts, PUT /api/v1/posts/{postId} 호출로 교체
    private fun complete() {
        val state = uiState.value
        if (!state.completeEnabled) return
        val mode = state.sheetMode ?: return
        val title = state.title.trim()
        val content = state.content.trim()

        when (mode) {
            PostSheetMode.Create -> dummyPosts += PostCardUiModel(
                id = nextId++,
                title = title,
                content = content,
                author = "흠나흠나",
                createdAt = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Date()),
            )

            is PostSheetMode.Edit -> {
                val index = dummyPosts.indexOfFirst { it.id == mode.postId }

                if (index == -1) {
                    showToast("게시글 수정에 실패했어요.")
                    return
                }

                dummyPosts[index] = dummyPosts[index].copy(title = title, content = content)
            }
        }

        closeSheet()
        refreshPosts()
    }

    // TODO: API 연동 시 DELETE /api/v1/posts/{postId} 호출로 교체
    private fun deletePost() {
        val postId = _uiState.value.deleteTargetId ?: return
        closeDeleteModal()

        if (dummyPosts.removeAll { it.id == postId }) {
            refreshPosts()
        } else {
            showToast("게시글 삭제에 실패했어요.")
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _sideEffect.send(PostsSideEffect.ShowToast(message))
        }
    }
}
