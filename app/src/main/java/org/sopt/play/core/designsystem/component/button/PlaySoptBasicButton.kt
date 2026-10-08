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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun PlaySoptBasicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val colors = PlaySoptTheme.colors
    val backgroundColor = if (enabled) colors.black else colors.gray1
    val textColor = if (enabled) colors.gray1 else colors.gray3

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .noRippleClickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            style = PlaySoptTheme.typography.sb14,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaySoptBasicButtonPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            PlaySoptBasicButton(
                text = "로그인",
                onClick = {},
            )
            PlaySoptBasicButton(
                text = "로그인",
                onClick = {},
                enabled = false,
            )
        }
    }
}