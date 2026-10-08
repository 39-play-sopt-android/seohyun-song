package org.sopt.play.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Black = Color(0xFF121212)
val White = Color(0xFFFFFFFF)

val Gray1 = Color(0xFFF7F7F7)
val Gray2 = Color(0xFFD1D5D6)
val Gray3 = Color(0xFFB2BABD)
val Gray5 = Color(0xFF505559)
val Gray6 = Color(0xFF23272A)

val Red = Color(0xFFFF4D4D)

@Immutable
data class PlaySoptColors(
    val black: Color,
    val white: Color,
    val gray1: Color,
    val gray2: Color,
    val gray3: Color,
    val gray5: Color,
    val gray6: Color,
    val red: Color,
)

val defaultPlaySoptColors = PlaySoptColors(
    black = Black,
    white = White,
    gray1 = Gray1,
    gray2 = Gray2,
    gray3 = Gray3,
    gray5 = Gray5,
    gray6 = Gray6,
    red = Red,
)

@Preview(showBackground = true, backgroundColor= 0)
@Composable
private fun ColorPreview() {
    PlaySoptTheme {
        Column {
            listOf(
                PlaySoptTheme.colors.black,
                PlaySoptTheme.colors.white,

                PlaySoptTheme.colors.gray1,
                PlaySoptTheme.colors.gray2,
                PlaySoptTheme.colors.gray3,
                PlaySoptTheme.colors.gray5,
                PlaySoptTheme.colors.gray6,

                PlaySoptTheme.colors.red,
            ).chunked(4).forEach { rowColors ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    rowColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(horizontal = 5.dp)
                                .background(
                                    color = color,
                                    shape = RoundedCornerShape(2.dp),
                                )
                        )
                    }
                }
            }
        }
    }
}