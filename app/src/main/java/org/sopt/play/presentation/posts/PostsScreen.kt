package org.sopt.play.presentation.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.core.designsystem.component.button.PlaySoptFloatingButton
import org.sopt.play.core.designsystem.component.textfield.PlaySoptSearchTextField
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.showToast
import org.sopt.play.presentation.posts.component.PostBottomSheet
import org.sopt.play.presentation.posts.component.PostCard
import org.sopt.play.presentation.posts.component.PostModal
import org.sopt.play.presentation.posts.model.PostCardUiModel

@Composable
fun PostsRoute(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = viewModel(),
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is PostsSideEffect.ShowToast -> context.showToast(sideEffect.message)
                }
            }
        }
    }

    PostsScreen(
        uiState = uiState,
        searchState = viewModel.searchState,
        onSearch = { viewModel.onIntent(PostsIntent.SearchClick) },
        onPostClick = { viewModel.onIntent(PostsIntent.PostClick(it)) },
        onPostLongClick = { viewModel.onIntent(PostsIntent.PostLongClick(it)) },
        onAddClick = { viewModel.onIntent(PostsIntent.AddClick) },
        modifier = modifier,
    )

    if (uiState.sheetMode != null) {
        PostBottomSheet(
            titleState = viewModel.titleState,
            contentState = viewModel.contentState,
            isCompleteEnabled = uiState.completeEnabled,
            onCompleteClick = { viewModel.onIntent(PostsIntent.CompleteClick) },
            onDismissRequest = { viewModel.onIntent(PostsIntent.SheetDismiss) },
        )
    }

    if (uiState.deleteTargetId != null) {
        PostModal(
            onDeleteClick = { viewModel.onIntent(PostsIntent.DeleteClick) },
            onDismissRequest = { viewModel.onIntent(PostsIntent.ModalDismiss) },
        )
    }
}

@Composable
fun PostsScreen(
    uiState: PostsUiState,
    searchState: TextFieldState,
    onSearch: () -> Unit,
    onPostClick: (postId: Long) -> Unit,
    onPostLongClick: (postId: Long) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white)
            .padding(horizontal = 16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlaySoptSearchTextField(
                state = searchState,
                onSearch = onSearch,
                modifier = Modifier.padding(vertical = 12.dp),
            )

            if (uiState.showEmpty) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = uiState.emptyMessage,
                        color = PlaySoptTheme.colors.gray6,
                        style = PlaySoptTheme.typography.m18,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 152.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = uiState.posts,
                        key = { it.id },
                    ) { post ->
                        PostCard(
                            post = post,
                            onClick = { onPostClick(post.id) },
                            onLongClick = { onPostLongClick(post.id) },
                        )
                    }
                }
            }
        }

        PlaySoptFloatingButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp),
        )
    }
}

@Preview
@Composable
private fun PostsScreenPreview() {
    PlaySoptTheme {
        PostsScreen(
            uiState = PostsUiState(
                posts = List(8) { index ->
                    PostCardUiModel(
                        id = index.toLong(),
                        title = "글쓰기 테스트띠니",
                        content = "본문이지롱 ".repeat(if (index % 4 == 0) 12 else 3),
                        author = "흠나흠나",
                        createdAt = "2026.11.26",
                    )
                },
            ),
            searchState = rememberTextFieldState(),
            onSearch = {},
            onPostClick = {},
            onPostLongClick = {},
            onAddClick = {},
        )
    }
}
