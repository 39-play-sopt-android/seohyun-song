package org.sopt.play.presentation.main.component

import androidx.annotation.DrawableRes
import org.sopt.play.R
import org.sopt.play.core.navigation.MainTabRoute
import org.sopt.play.presentation.favorite.navigation.Favorite
import org.sopt.play.presentation.home.navigation.Home
import org.sopt.play.presentation.mypage.navigation.MyPage

enum class MainTab(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    val title: String,
    val route: MainTabRoute,
) {
    HOME(
        selectedIconRes = R.drawable.ic_home_filled_24,
        unselectedIconRes = R.drawable.ic_home_default_24,
        title = "홈",
        route = Home,
    ),

    FAVORITE(
        selectedIconRes = R.drawable.ic_favorite_filled_24,
        unselectedIconRes = R.drawable.ic_favorite_default_24,
        title = "찜",
        route = Favorite,
    ),

    MYPAGE(
        selectedIconRes = R.drawable.ic_user_filled_24,
        unselectedIconRes = R.drawable.ic_user_default_24,
        title = "마이페이지",
        route = MyPage,
    )
}
