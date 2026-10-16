package org.sopt.play.presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun FavoriteRoute(
    modifier: Modifier = Modifier,
) {
    FavoriteScreen(
        modifier = modifier,
    )
}

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "찜",
            style = PlaySoptTheme.typography.sb14,
            color = PlaySoptTheme.colors.black,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    PlaySoptTheme {
        FavoriteScreen()
    }
}
