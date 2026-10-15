package org.sopt.play.presentation.auth.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.Route
import org.sopt.play.presentation.auth.login.LoginRoute

fun NavGraphBuilder.loginGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Login> {
        LoginRoute(
            registeredEmail = null,
            registeredPassword = null,
            onLoginSuccess = {},
            onRegisterClick = {},
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Login : Route
