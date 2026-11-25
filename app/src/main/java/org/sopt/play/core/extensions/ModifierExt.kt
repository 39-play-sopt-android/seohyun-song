package org.sopt.play.core.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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

/**
 * 리플 효과 없이 클릭 / 롱클릭을 받는 Modifier
 *
 * @param onClick 클릭 시 실행될 콜백
 * @param onLongClick 길게 눌렀을 때 실행될 콜백
 */
fun Modifier.noRippleCombinedClickable(
    enabled: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit,
): Modifier =
    combinedClickable(
        interactionSource = null,
        indication = null,
        enabled = enabled,
        onLongClick = onLongClick,
        onClick = onClick,
    )
