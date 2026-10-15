package org.sopt.play.presentation.auth.register.navigation

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
import org.sopt.play.presentation.auth.register.RegisterRoute

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null
) = navigate(Register, navOptions)

fun NavGraphBuilder.registerGraph(
    onRegisterSuccess: (email: String, password: String) -> Unit,
    innerPadding: PaddingValues,
) {
    composable<Register> {
        RegisterRoute(
            onRegisterSuccess = onRegisterSuccess,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        )
    }
}

@Serializable
data object Register : Route
