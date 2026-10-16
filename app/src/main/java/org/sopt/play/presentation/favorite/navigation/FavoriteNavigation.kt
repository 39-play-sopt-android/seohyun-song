package org.sopt.play.presentation.favorite.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.MainTabRoute
import org.sopt.play.presentation.favorite.FavoriteRoute

fun NavController.navigateToFavorite(
    navOptions: NavOptions? = null
) = navigate(Favorite, navOptions)

fun NavGraphBuilder.favoriteGraph(
    innerPadding: PaddingValues,
) {
    composable<Favorite> {
        FavoriteRoute(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Serializable
data object Favorite : MainTabRoute
