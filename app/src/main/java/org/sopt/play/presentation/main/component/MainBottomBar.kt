package org.sopt.play.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun MainBottomBar(
    isVisible: Boolean,
    tabs: List<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
        modifier = modifier,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = PlaySoptTheme.colors.gray2,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = PlaySoptTheme.colors.white,
                    )
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEach { tab ->
                    key(tab.route) {
                        MainBottomBarItem(
                            tab = tab,
                            isSelected = tab == currentTab,
                            onClick = { onTabSelected(tab) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainBottomBarItem(
    tab: MainTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isSelected) {
        PlaySoptTheme.colors.black
    } else {
        PlaySoptTheme.colors.gray5
    }

    Column(
        modifier = modifier.noRippleClickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                if (isSelected) tab.selectedIconRes
                else tab.unselectedIconRes
            ),
            contentDescription = tab.title,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified,
        )

        Text(
            text = tab.title,
            style = PlaySoptTheme.typography.sb14,
            color = contentColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomBarPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            MainBottomBar(
                isVisible = true,
                tabs = MainTab.entries,
                currentTab = MainTab.HOME,
                onTabSelected = { },
            )
            MainBottomBar(
                isVisible = true,
                tabs = MainTab.entries,
                currentTab = MainTab.FAVORITE,
                onTabSelected = { },
            )
            MainBottomBar(
                isVisible = true,
                tabs = MainTab.entries,
                currentTab = MainTab.MYPAGE,
                onTabSelected = { },
            )
        }
    }
}
