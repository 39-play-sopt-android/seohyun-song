package org.sopt.play.presentation.auth.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.Route
import org.sopt.play.presentation.auth.login.LoginRoute

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) = navigate(Login, navOptions)

fun NavGraphBuilder.loginGraph(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    innerPadding: PaddingValues,
) {
    composable<Login> {
        LoginRoute(
            registeredEmail = null,
            registeredPassword = null,
            onLoginSuccess = navigateToHome,
            onRegisterClick = navigateToRegister,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Login : Route
