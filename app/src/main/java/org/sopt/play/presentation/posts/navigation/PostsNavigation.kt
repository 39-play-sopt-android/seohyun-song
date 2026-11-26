package org.sopt.play.presentation.posts.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.Route
import org.sopt.play.presentation.posts.PostsRoute

fun NavController.navigateToPosts(
    navOptions: NavOptions ?= null
) = navigate(Posts, navOptions)

fun NavGraphBuilder.postsGraph(
    innerPadding: PaddingValues,
) {
    composable<Posts> {
        PostsRoute(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}

@Serializable
data object Posts : Route
