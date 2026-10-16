package org.sopt.play.presentation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import org.sopt.play.presentation.auth.login.navigation.Login
import org.sopt.play.presentation.auth.login.navigation.loginGraph
import org.sopt.play.presentation.auth.login.navigation.popBackStackToLogin
import org.sopt.play.presentation.auth.register.navigation.navigateToRegister
import org.sopt.play.presentation.auth.register.navigation.registerGraph
import org.sopt.play.presentation.favorite.navigation.favoriteGraph
import org.sopt.play.presentation.home.navigation.homeGraph
import org.sopt.play.presentation.home.navigation.navigateToHome
import org.sopt.play.presentation.mypage.navigation.myPageGraph

private const val NAVIGATION_ANIMATION_DURATION_MILLIS = 700

@Composable
fun MainNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIMATION_DURATION_MILLIS)) },
        exitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIMATION_DURATION_MILLIS)) },
    ) {
        loginGraph(
            navigateToRegister = { navController.navigateToRegister() },
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<Login> { inclusive = true }
                    }
                )
            },
            innerPadding = innerPadding,
        )

        registerGraph(
            onRegisterSuccess = { email, password ->
                navController.popBackStackToLogin(
                    registeredEmail = email,
                    registeredPassword = password,
                )
            },
            innerPadding = innerPadding,
        )

        homeGraph(
            innerPadding = innerPadding,
        )

        favoriteGraph(
            innerPadding = innerPadding,
        )

        myPageGraph(
            innerPadding = innerPadding,
        )
    }
}
