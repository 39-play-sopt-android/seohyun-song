package org.sopt.play.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun HomeTabMenu(
    menu: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSelected) PlaySoptTheme.colors.black
    else PlaySoptTheme.colors.gray3

    val dotColor = if (isSelected) PlaySoptTheme.colors.black
    else Color.Transparent

    Column(
        modifier
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = dotColor, shape = CircleShape
                ),
        )

        Text(
            text = menu,
            color = textColor,
            style = PlaySoptTheme.typography.m18,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTabMenuPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier.padding(all = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HomeTabMenu(
                menu = "메뉴",
                isSelected = true,
                onClick = {},
            )

            HomeTabMenu(
                menu = "메뉴",
                isSelected = false,
                onClick = {},
            )
        }
    }
}
