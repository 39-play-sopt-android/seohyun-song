package org.sopt.play.presentation.home

import org.sopt.play.presentation.home.model.HomeCategory
import org.sopt.play.presentation.home.model.HomeMenu
import org.sopt.play.presentation.home.model.ProductModel

data class HomeUiState(
    val userName: String = "김솝트",
    val bannerList: List<Int> = HomeDummyData.banners,
    val productList: List<ProductModel> = HomeDummyData.products,
    val selectedMenu: HomeMenu = HomeMenu.HOME,
    val selectedCategory: HomeCategory = HomeCategory.ALL,
    val favoriteProductIds: Set<Int> = setOf(2),
)
