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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.core.di.appContainer
import org.sopt.play.data.remote.serverMessage
import org.sopt.play.data.repository.AuthRepository
import org.sopt.play.data.repository.PostRepository
import org.sopt.play.presentation.posts.model.PostSheetMode.Create
import org.sopt.play.presentation.posts.model.PostSheetMode.Edit
import org.sopt.play.presentation.posts.model.toUiModel

class PostsViewModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
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

    private var refreshJob: Job? = null
    private var submitJob: Job? = null

    init {
        observeMyName()
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
        val keyword = searchState.text.toString().trim()

        if (keyword.isNotEmpty() && keyword.length < SEARCH_KEYWORD_MIN_LENGTH) {
            showToast("검색어는 ${SEARCH_KEYWORD_MIN_LENGTH}자 이상 입력해 주세요.")
            return
        }

        _uiState.update { it.copy(keyword = keyword) }
        refreshPosts()
    }

    private fun observeMyName() {
        authRepository.registeredName
            .onEach { name -> _uiState.update { it.copy(myName = name) } }
            .launchIn(viewModelScope)
    }

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
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    showToast(error.serverMessage() ?: "게시글을 불러오지 못했어요.")
                }
        }
    }

    private fun openCreateSheet() {
        if (_uiState.value.myName == null) {
            showToast("회원 정보에 이름이 없어요. 다시 회원가입해 주세요.")
            return
        }

        titleState.clearText()
        contentState.clearText()

        _uiState.update {
            it.copy(sheetMode = Create)
        }
    }

    private fun openEditSheet(postId: Long) {
        val state = _uiState.value
        val post = state.posts.find { it.id == postId } ?: return

        if (!state.isMine(post)) {
            showToast(NOT_MINE_MESSAGE)
            return
        }

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
        val state = _uiState.value
        val post = state.posts.find { it.id == postId } ?: return

        if (!state.isMine(post)) {
            showToast(NOT_MINE_MESSAGE)
            return
        }

        _uiState.update { it.copy(deleteTargetId = postId) }
    }

    private fun closeDeleteModal() {
        _uiState.update { it.copy(deleteTargetId = null) }
    }

    private fun complete() {
        if (submitJob?.isActive == true) return

        val state = uiState.value
        if (!state.completeEnabled) return
        val mode = state.sheetMode ?: return
        val title = state.title.trim()
        val content = state.content.trim()

        closeSheet()

        submitJob = viewModelScope.launch {
            val result = when (mode) {
                Create -> postRepository.createPost(title, content)
                is Edit -> postRepository.updatePost(mode.postId, title, content)
            }

            result
                .onSuccess {
                    if (mode is Create) clearSearch()
                    refreshPosts()
                }
                .onFailure { error ->
                    val fallback = if (mode is Edit) "게시글 수정에 실패했어요." else "게시글 작성에 실패했어요."
                    showToast(error.serverMessage() ?: fallback)
                }
        }
    }

    private fun clearSearch() {
        searchState.clearText()
        _uiState.update { it.copy(keyword = "") }
    }

    private fun deletePost() {
        val postId = _uiState.value.deleteTargetId ?: return
        closeDeleteModal()

        viewModelScope.launch {
            postRepository.deletePost(postId)
                .onSuccess { refreshPosts() }
                .onFailure { showToast(it.serverMessage() ?: "게시글 삭제에 실패했어요.") }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _sideEffect.send(PostsSideEffect.ShowToast(message))
        }
    }

    companion object {
        private const val NOT_MINE_MESSAGE = "내가 쓴 글만 수정/삭제할 수 있어요."

        val Factory = viewModelFactory {
            initializer {
                PostsViewModel(
                    postRepository = appContainer.postRepository,
                    authRepository = appContainer.authRepository,
                )
            }
        }
    }
}
