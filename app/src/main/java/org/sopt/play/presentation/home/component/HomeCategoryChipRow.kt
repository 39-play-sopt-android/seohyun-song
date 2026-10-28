package org.sopt.play.presentation.home.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
fun HomeCategoryChipRow(
    selectedCategory: HomeCategory,
    onCategoryClick: (HomeCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        HomeCategory.entries.forEach { category ->
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
private fun HomeCategoryChipRowPreview() {
    PlaySoptTheme {
        var selectedCategory by remember { mutableStateOf(HomeCategory.ALL) }

        HomeCategoryChipRow(
            selectedCategory = selectedCategory,
            onCategoryClick = { selectedCategory = it },
        )
    }
}
