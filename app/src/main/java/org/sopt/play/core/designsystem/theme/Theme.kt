package org.sopt.play.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalPlaySoptColors = staticCompositionLocalOf { defaultPlaySoptColors }

private val LocalPlaySoptTypography = staticCompositionLocalOf { defaultPlaySoptTypography }

private val PlaySoptScheme = lightColorScheme(
    background = White,
)

object PlaySoptTheme {
    val colors: PlaySoptColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaySoptColors.current

    val typography: PlaySoptTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaySoptTypography.current
}

@Composable
fun PlaySoptTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalPlaySoptColors provides defaultPlaySoptColors,
        LocalPlaySoptTypography provides defaultPlaySoptTypography,
    ) {
        MaterialTheme(
            colorScheme = PlaySoptScheme,
            content = content,
        )
    }
}