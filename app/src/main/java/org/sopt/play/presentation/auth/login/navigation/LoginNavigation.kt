package org.sopt.play.presentation.auth.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.Route
import org.sopt.play.presentation.auth.AuthResultKeys
import org.sopt.play.presentation.auth.login.LoginRoute

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) = navigate(Login, navOptions)

fun NavController.popBackStackToLogin(
    registeredEmail: String,
    registeredPassword: String,
) {
    getBackStackEntry<Login>().savedStateHandle.apply {
        set(AuthResultKeys.EMAIL, registeredEmail)
        set(AuthResultKeys.PASSWORD, registeredPassword)
    }
    popBackStack<Login>(inclusive = false)
}

fun NavGraphBuilder.loginGraph(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    innerPadding: PaddingValues,
) {
    composable<Login> { backStackEntry ->
        val registeredEmail by backStackEntry.savedStateHandle
            .getStateFlow<String?>(AuthResultKeys.EMAIL, null)
            .collectAsStateWithLifecycle()
        val registeredPassword by backStackEntry.savedStateHandle
            .getStateFlow<String?>(AuthResultKeys.PASSWORD, null)
            .collectAsStateWithLifecycle()

        LoginRoute(
            registeredEmail = registeredEmail,
            registeredPassword = registeredPassword,
            onLoginSuccess = navigateToHome,
            onRegisterClick = navigateToRegister,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Login : Route
