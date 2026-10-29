package org.sopt.play.presentation.home.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.model.HomeMenu

@Composable
fun HomeMenuTabRow(
    selectedMenu: HomeMenu,
    onMenuClick: (HomeMenu) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
    ) {
        HomeMenu.entries.forEach { menu ->
            HomeTabMenu(
                menu = menu.title,
                isSelected = menu == selectedMenu,
                onClick = { onMenuClick(menu) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeMenuTabRowPreview() {
    PlaySoptTheme {
        var selectedMenu by remember { mutableStateOf(HomeMenu.HOME) }

        HomeMenuTabRow(
            selectedMenu = selectedMenu,
            onMenuClick = { selectedMenu = it },
        )
    }
}
