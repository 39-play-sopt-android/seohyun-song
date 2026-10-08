package org.sopt.play.core.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.sopt.play.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
)

private fun playSoptTextStyle(
    weight: FontWeight,
    size: Int,
): TextStyle = TextStyle(
    fontFamily = Pretendard,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = 1.2.em,
    letterSpacing = (-0.01).em,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
)

@Immutable
data class PlaySoptTypography(
    val b28: TextStyle = playSoptTextStyle(weight = FontWeight.Bold, size = 28),
    val m18: TextStyle = playSoptTextStyle(weight = FontWeight.Medium, size = 18),
    val sb16: TextStyle = playSoptTextStyle(weight = FontWeight.SemiBold, size = 16),
    val m14: TextStyle = playSoptTextStyle(weight = FontWeight.Medium, size = 14),
    val sb14: TextStyle = playSoptTextStyle(weight = FontWeight.SemiBold, size = 14),
)

val defaultPlaySoptTypography = PlaySoptTypography()

@Preview(showBackground = true)
@Composable
private fun TypographyPreview() {
    PlaySoptTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("b28", style = PlaySoptTheme.typography.b28)
            Text("m18", style = PlaySoptTheme.typography.m18)
            Text("sb16", style = PlaySoptTheme.typography.sb16)
            Text("m14", style = PlaySoptTheme.typography.m14)
            Text("sb14", style = PlaySoptTheme.typography.sb14)
        }
    }
}