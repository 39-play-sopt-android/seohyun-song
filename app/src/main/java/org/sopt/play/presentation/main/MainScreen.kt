package org.sopt.play.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.play.presentation.main.component.MainBottomBar
import org.sopt.play.presentation.main.component.MainTab

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appState: MainAppState = rememberMainAppState(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(
                isVisible = appState.shouldShowBottomBar(),
                tabs = MainTab.entries,
                currentTab = appState.currentTab,
                onTabSelected = appState::navigateToTab,
                modifier = Modifier.navigationBarsPadding(),
            )
        },
    ) { innerPadding ->
        MainNavHost(
            navController = appState.navController,
            innerPadding = innerPadding,
        )
    }
}
