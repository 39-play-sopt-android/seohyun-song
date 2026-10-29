package org.sopt.play.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.sopt.play.presentation.home.navigation.Home
import org.sopt.play.presentation.main.component.MainTab

@Stable
class MainAppState(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() = MainTab.entries.find { tab ->
            currentDestination?.hasRoute(tab.route::class) == true
        }

    @Composable
    fun shouldShowBottomBar(): Boolean = currentTab != null

    fun navigateToTab(tab: MainTab) {
        navController.navigate(tab.route) {
            popUpTo<Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
): MainAppState = remember(navController) {
    MainAppState(navController)
}
