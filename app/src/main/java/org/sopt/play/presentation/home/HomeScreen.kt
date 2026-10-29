package org.sopt.play.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.preview.FontScalePreviews
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.component.HomeBanner
import org.sopt.play.presentation.home.component.HomeCategoryChipRow
import org.sopt.play.presentation.home.component.HomeMenuTabRow
import org.sopt.play.presentation.home.component.HomeProductCard
import org.sopt.play.presentation.home.component.HomeTopBar
import org.sopt.play.presentation.home.model.HomeCategory
import org.sopt.play.presentation.home.model.HomeMenu
import org.sopt.play.presentation.home.model.ProductModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
) {
    var selectedMenu by remember { mutableStateOf(HomeMenu.HOME) }
    var selectedCategory by remember { mutableStateOf(HomeCategory.ALL) }
    var favoriteProductIds by remember { mutableStateOf(setOf(2)) }

    HomeScreen(
        userName = "김솝트",
        bannerList = HomeDummyData.banners,
        productList = HomeDummyData.products,
        selectedMenu = selectedMenu,
        selectedCategory = selectedCategory,
        favoriteProductIds = favoriteProductIds,
        onSearchClick = {},
        onMenuClick = { selectedMenu = it },
        onCategoryClick = { selectedCategory = it },
        onFavoriteClick = { productId ->
            favoriteProductIds = if (productId in favoriteProductIds) favoriteProductIds - productId
            else favoriteProductIds + productId
        },
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    userName: String,
    bannerList: List<Int>,
    productList: List<ProductModel>,
    selectedMenu: HomeMenu,
    selectedCategory: HomeCategory,
    favoriteProductIds: Set<Int>,
    onSearchClick: () -> Unit,
    onMenuClick: (HomeMenu) -> Unit,
    onCategoryClick: (HomeCategory) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white),
    ) {
        HomeTopBar(
            onSearchClick = onSearchClick,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 96.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeMenuTabRow(
                    selectedMenu = selectedMenu,
                    onMenuClick = onMenuClick,
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeBanner(
                    bannerList = bannerList,
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "${userName}님을 위한 추천 아이템",
                        color = PlaySoptTheme.colors.black,
                        style = PlaySoptTheme.typography.m18,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HomeCategoryChipRow(
                        selectedCategory = selectedCategory,
                        onCategoryClick = onCategoryClick,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            items(
                items = productList,
                key = { product -> product.id },
            ) { product ->
                HomeProductCard(
                    product = product,
                    isFavorite = product.id in favoriteProductIds,
                    onFavoriteClick = { onFavoriteClick(product.id) },
                )
            }
        }
    }
}

@FontScalePreviews
@Composable
private fun HomePreview() {
    PlaySoptTheme {
        HomeRoute()
    }
}
