package org.sopt.play.presentation.posts

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Job
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
import org.sopt.play.core.di.appContainer
import org.sopt.play.data.repository.PostRepository
import org.sopt.play.presentation.posts.model.PostSheetMode.Create
import org.sopt.play.presentation.posts.model.PostSheetMode.Edit
import org.sopt.play.presentation.posts.model.toUiModel

class PostsViewModel(
    private val postRepository: PostRepository,
) : ViewModel() {
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

    private var refreshJob: Job? = null
    private fun refreshPosts() {
        val keyword = _uiState.value.keyword.ifBlank { null }
        _uiState.update { it.copy(isLoading = true) }

        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            postRepository.getPosts(keyword)
                .onSuccess { posts ->
                    _uiState.update {
                        it.copy(posts = posts.map { dto -> dto.toUiModel() }, isLoading = false)
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    showToast("게시글을 불러오지 못했어요.")
                }
        }
    }

    private fun openCreateSheet() {
        titleState.clearText()
        contentState.clearText()

        _uiState.update {
            it.copy(sheetMode = Create)
        }
    }

    private fun openEditSheet(postId: Long) {
        val post = _uiState.value.posts.find { it.id == postId } ?: return

        titleState.setTextAndPlaceCursorAtEnd(post.title)
        contentState.setTextAndPlaceCursorAtEnd(post.content)

        _uiState.update {
            it.copy(sheetMode = Edit(post.id, post.title, post.content))
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

    private fun complete() {
        val state = uiState.value
        if (!state.completeEnabled) return
        val mode = state.sheetMode ?: return
        val title = state.title.trim()
        val content = state.content.trim()

        viewModelScope.launch {
            val result = when (mode) {
                Create -> postRepository.createPost(title, content)
                is Edit -> postRepository.updatePost(mode.postId, title, content)
            }

            result
                .onSuccess {
                    closeSheet()
                    refreshPosts()
                }
                .onFailure {
                    showToast(
                        if (mode is Edit) "게시글 수정에 실패했어요." else "게시글 작성에 실패했어요."
                    )
                }
        }
    }

    private fun deletePost() {
        val postId = _uiState.value.deleteTargetId ?: return
        closeDeleteModal()

        viewModelScope.launch {
            postRepository.deletePost(postId)
                .onSuccess { refreshPosts() }
                .onFailure { showToast("게시글 삭제에 실패했어요.") }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _sideEffect.send(PostsSideEffect.ShowToast(message))
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer { PostsViewModel(appContainer.postRepository) }
        }
    }
}
