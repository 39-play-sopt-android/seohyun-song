package org.sopt.play.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun HomeChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSelected) PlaySoptTheme.colors.white
    else PlaySoptTheme.colors.gray5

    val backgroundColor = if (isSelected) PlaySoptTheme.colors.gray5
    else PlaySoptTheme.colors.white

    val borderColor = if (isSelected) PlaySoptTheme.colors.gray5
    else PlaySoptTheme.colors.gray2

    val shape = RoundedCornerShape(6.dp)

    Text(
        text = category,
        color = textColor,
        style = PlaySoptTheme.typography.m14,
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .background(
                color = backgroundColor,
                shape = shape,
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape,
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeChipPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier.padding(all = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HomeChip(
                category = "칩",
                isSelected = true,
                onClick = {},
            )

            HomeChip(
                category = "칩",
                isSelected = false,
                onClick = {},
            )
        }
    }
}
