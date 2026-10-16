package org.sopt.play.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.sopt.play.core.navigation.MainTabRoute
import org.sopt.play.presentation.mypage.MyPageRoute

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null
) = navigate(MyPage, navOptions)

fun NavGraphBuilder.myPageGraph(
    innerPadding: PaddingValues,
) {
    composable<MyPage> {
        MyPageRoute(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Serializable
data object MyPage : MainTabRoute
