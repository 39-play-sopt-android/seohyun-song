package org.sopt.play.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.presentation.main.component.MainBottomBar
import org.sopt.play.presentation.main.component.MainTab

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appState: MainAppState = rememberMainAppState(),
    viewModel: MainViewModel = viewModel(factory = MainViewModel.Factory),
) {
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

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
        startDestination?.let { destination ->
            MainNavHost(
                navController = appState.navController,
                startDestination = destination,
                innerPadding = innerPadding,
            )
        }
    }
}
