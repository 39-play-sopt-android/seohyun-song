package org.sopt.play.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PlaySoptTheme.colors.white)
            .padding(start = 16.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val density = LocalDensity.current

        CompositionLocalProvider(
            LocalDensity provides Density(density = density.density, fontScale = 1f),
        ) {
            Text(
                text = "PLAY SOPT",
                style = PlaySoptTheme.typography.b28,
                color = PlaySoptTheme.colors.black,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_search_24),
            contentDescription = "검색",
            tint = PlaySoptTheme.colors.black,
            modifier = Modifier
                .noRippleClickable(onClick = onSearchClick)
                .padding(all = 10.dp)
                .size(28.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    PlaySoptTheme {
        HomeTopBar(
            onSearchClick = {},
        )
    }
}
