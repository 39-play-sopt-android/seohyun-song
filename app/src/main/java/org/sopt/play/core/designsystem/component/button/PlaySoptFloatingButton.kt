package org.sopt.play.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun PlaySoptFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 12.dp,
                    offset = DpOffset(x = 0.dp, y = 4.dp),
                    color = Color.Black.copy(alpha = 0.15f),
                ),
            )
            .clip(CircleShape)
            .background(color = PlaySoptTheme.colors.red)
            .noRippleClickable(onClick = onClick)
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_plus_28),
            contentDescription = null,
            tint = PlaySoptTheme.colors.white,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaySoptFloatingButtonPreview() {
    PlaySoptTheme {
        Box(modifier = Modifier.padding(20.dp)) {
            PlaySoptFloatingButton(onClick = {})
        }
    }
}
