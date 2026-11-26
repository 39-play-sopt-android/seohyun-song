package org.sopt.play.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.designsystem.type.PlaySoptButtonType
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun PlaySoptButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: PlaySoptButtonType = PlaySoptButtonType.PRIMARY,
) {
    val colors = PlaySoptTheme.colors
    val (backgroundColor, textColor) = when (type) {
        PlaySoptButtonType.PRIMARY -> colors.black to colors.gray1
        PlaySoptButtonType.SECONDARY -> colors.gray1 to colors.gray5
        PlaySoptButtonType.DISABLED -> colors.gray1 to colors.gray3
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = backgroundColor)
            .noRippleClickable(
                enabled = type != PlaySoptButtonType.DISABLED,
                onClick = onClick,
            )
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            style = PlaySoptTheme.typography.sb14,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaySoptButtonPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            PlaySoptButtonType.entries.forEach { type ->
                PlaySoptButton(
                    text = "로그인",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    type = type,
                )
            }
        }
    }
}