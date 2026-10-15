package org.sopt.play.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.MainTabRoute
import org.sopt.play.presentation.home.HomeRoute

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    innerPadding: PaddingValues,
) {
    composable<Home> {
        HomeRoute(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Serializable
data object Home : MainTabRoute
