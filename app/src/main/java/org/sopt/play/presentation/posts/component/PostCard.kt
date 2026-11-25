package org.sopt.play.presentation.posts.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleCombinedClickable
import org.sopt.play.presentation.posts.model.PostCardUiModel

@Composable
fun PostCard(
    post: PostCardUiModel,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PlaySoptTheme.colors.gray1,
                shape = RoundedCornerShape(20.dp),
            )
            .noRippleCombinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(all = 20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = post.author,
                color = PlaySoptTheme.colors.gray3,
                style = PlaySoptTheme.typography.m12,
            )

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(12.dp)
                    .background(color = PlaySoptTheme.colors.gray3),
            )

            Text(
                text = post.createdAt,
                color = PlaySoptTheme.colors.gray3,
                style = PlaySoptTheme.typography.m12,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = post.title,
            color = PlaySoptTheme.colors.gray5,
            style = PlaySoptTheme.typography.m16,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = post.content,
            color = PlaySoptTheme.colors.gray5,
            style = PlaySoptTheme.typography.m14,
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PostCardPreview() {
    PlaySoptTheme {
        PostCard(
            post = PostCardUiModel(
                id = 1L,
                title = "글쓰기 테스트띠니",
                content = "본문이지롱 ".repeat(12),
                author = "훔나훔나",
                createdAt = "2026.11.25",
            ),
            onClick = {},
            onLongClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
