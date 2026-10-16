package org.sopt.play.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.preview.FontScalePreviews
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
) {
    MyPageScreen(
        modifier = modifier,
    )
}

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "마이페이지",
            style = PlaySoptTheme.typography.sb14,
            color = PlaySoptTheme.colors.black,
        )
    }
}

@FontScalePreviews
@Composable
private fun MyPagePreview() {
    PlaySoptTheme {
        MyPageScreen()
    }
}
