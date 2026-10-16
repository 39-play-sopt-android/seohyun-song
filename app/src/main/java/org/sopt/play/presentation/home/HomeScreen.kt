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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.data.model.home.ProductModel
import org.sopt.play.presentation.home.component.HomeBanner
import org.sopt.play.presentation.home.component.HomeCategories
import org.sopt.play.presentation.home.component.HomeMenus
import org.sopt.play.presentation.home.component.HomeProductCard
import org.sopt.play.presentation.home.component.HomeTopBar
import org.sopt.play.presentation.home.model.HomeCategory
import org.sopt.play.presentation.home.model.HomeMenu

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
) {
    var selectedMenu by remember { mutableStateOf(HomeMenu.HOME) }
    var selectedCategory by remember { mutableStateOf(HomeCategory.ALL) }
    var favoriteProductIds by remember { mutableStateOf(setOf(2)) }

    HomeScreen(
        userName = "김솝트",
        bannerList = dummyBanners,
        productList = dummyProducts,
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
                HomeMenus(
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

                    HomeCategories(
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

private val dummyBanners = listOf(
    R.drawable.banner_1,
    R.drawable.banner_2,
    R.drawable.banner_3,
)

private val dummyProducts = listOf(
    ProductModel(
        id = 1,
        storeName = "트랜디어패럴",
        productName = "[카프리1위🏆/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
        discountRate = 36,
        price = 28800,
        imageRes = R.drawable.product_1,
    ),
    ProductModel(
        id = 2,
        storeName = "슬로우앤드",
        productName = "[🍁가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
        discountRate = 50,
        price = 26820,
        imageRes = R.drawable.product_2,
    ),
    ProductModel(
        id = 3,
        storeName = "어텀",
        productName = "보울7부티셔츠",
        discountRate = 10,
        price = 17100,
        imageRes = R.drawable.product_3,
    ),
    ProductModel(
        id = 4,
        storeName = "슬로우앤드",
        productName = "[오픈할인중] made. 도브 클래식 탄탄핏 무지셔츠 - 4 color",
        discountRate = 40,
        price = 35200,
        imageRes = R.drawable.product_4,
    ),
)

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    PlaySoptTheme {
        HomeRoute()
    }
}
