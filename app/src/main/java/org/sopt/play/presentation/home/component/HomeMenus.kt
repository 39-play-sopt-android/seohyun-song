package org.sopt.play.presentation.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun HomeMenus(
    selectedMenu: HomeMenu,
    onMenuClick: (HomeMenu) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(
            items = HomeMenu.entries,
            key = { menu -> menu.name },
        ) { menu ->
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
private fun HomeMenusPreview() {
    PlaySoptTheme {
        var selectedMenu by remember { mutableStateOf(HomeMenu.HOME) }

        HomeMenus(
            selectedMenu = selectedMenu,
            onMenuClick = { selectedMenu = it },
        )
    }
}
