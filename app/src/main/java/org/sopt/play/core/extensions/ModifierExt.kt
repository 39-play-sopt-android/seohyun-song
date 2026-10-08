package org.sopt.play.core.extensions

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

/**
 * 리플 효과 없이 클릭 가능하게 만드는 Modifier
 *
 * @param onClick 클릭 시 실행될 콜백
 */
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier =
    clickable(
        interactionSource = null,
        indication = null,
        enabled = enabled,
        onClick = onClick,
    )