package org.sopt.play.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.model.HomeCategory

@Composable
fun HomeCategories(
    selectedCategory: HomeCategory,
    onCategoryClick: (HomeCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(
            items = HomeCategory.entries,
            key = { category -> category.name },
        ) { category ->
            HomeChip(
                category = category.title,
                isSelected = category == selectedCategory,
                onClick = { onCategoryClick(category) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeCategoriesPreview() {
    PlaySoptTheme {
        var selectedCategory by remember { mutableStateOf(HomeCategory.ALL) }

        HomeCategories(
            selectedCategory = selectedCategory,
            onCategoryClick = { selectedCategory = it },
        )
    }
}
