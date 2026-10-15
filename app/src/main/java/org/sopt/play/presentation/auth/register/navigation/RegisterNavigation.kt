package org.sopt.play.presentation.auth.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.Route
import org.sopt.play.presentation.auth.register.RegisterRoute

fun NavGraphBuilder.registerGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Register> {
        RegisterRoute(
            onRegisterSuccess = { _, _ -> },
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        )
    }
}

@Serializable
data object Register : Route
