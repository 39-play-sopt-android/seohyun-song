package org.sopt.play.presentation.posts.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.button.PlaySoptButton
import org.sopt.play.core.designsystem.component.modal.PlaySoptModal
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.designsystem.type.PlaySoptButtonType

@Composable
fun PostModal(
    onDeleteClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlaySoptModal(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Text(
            text = "게시글을 삭제할까요?",
            color = PlaySoptTheme.colors.gray6,
            style = PlaySoptTheme.typography.m18,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            PlaySoptButton(
                text = "취소",
                onClick = onDismissRequest,
                modifier = Modifier.weight(1f),
                type = PlaySoptButtonType.SECONDARY,
            )

            Spacer(modifier = Modifier.width(12.dp))

            PlaySoptButton(
                text = "삭제",
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f),
                type = PlaySoptButtonType.PRIMARY,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostModalPreview() {
    PlaySoptTheme {
        PostModal(
            onDeleteClick = {},
            onDismissRequest = {},
        )
    }
}
