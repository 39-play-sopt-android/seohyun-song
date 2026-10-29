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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.core.designsystem.preview.FontScalePreviews
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.component.HomeBanner
import org.sopt.play.presentation.home.component.HomeCategoryChipRow
import org.sopt.play.presentation.home.component.HomeMenuTabRow
import org.sopt.play.presentation.home.component.HomeProductCard
import org.sopt.play.presentation.home.component.HomeTopBar

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white),
    ) {
        HomeTopBar(
            onSearchClick = { onIntent(HomeIntent.SearchClick) },
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 96.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeMenuTabRow(
                    selectedMenu = uiState.selectedMenu,
                    onMenuClick = { onIntent(HomeIntent.MenuClick(it)) },
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeBanner(
                    bannerList = uiState.bannerList,
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "${uiState.userName}님을 위한 추천 아이템",
                        color = PlaySoptTheme.colors.black,
                        style = PlaySoptTheme.typography.m18,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HomeCategoryChipRow(
                        selectedCategory = uiState.selectedCategory,
                        onCategoryClick = { onIntent(HomeIntent.CategoryClick(it)) },
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            items(
                items = uiState.productList,
                key = { product -> product.id },
            ) { product ->
                HomeProductCard(
                    product = product,
                    isFavorite = product.id in uiState.favoriteProductIds,
                    onFavoriteClick = { onIntent(HomeIntent.FavoriteClick(product.id)) },
                )
            }
        }
    }
}

@FontScalePreviews
@Composable
private fun HomePreview() {
    PlaySoptTheme {
        HomeScreen(
            uiState = HomeUiState(),
            onIntent = {},
        )
    }
}
