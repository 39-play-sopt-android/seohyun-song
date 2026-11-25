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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.button.PlaySoptFloatingButton
import org.sopt.play.core.designsystem.component.textfield.PlaySoptSearchTextField
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.posts.component.PostCard
import org.sopt.play.presentation.posts.model.PostCardUiModel

@Composable
fun PostsRoute(
    modifier: Modifier = Modifier,
) {
    val searchState = rememberTextFieldState()

    PostsScreen(
        uiState = PostsUiState(),
        searchState = searchState,
        onSearch = {},
        onPostClick = {},
        onPostLongClick = {},
        onAddClick = {},
        modifier = modifier,
    )
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
