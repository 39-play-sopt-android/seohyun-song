package org.sopt.play.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun PlaySoptSearchTextField(
    state: TextFieldState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "검색어를 입력하세요",
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor = if (isFocused) PlaySoptTheme.colors.gray5 else PlaySoptTheme.colors.gray2
    val textStyle = PlaySoptTheme.typography.m18.copy(color = PlaySoptTheme.colors.gray5)

    val decorator = TextFieldDecorator { innerTextField ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PlaySoptTheme.colors.white,
                    shape = RoundedCornerShape(12.dp),
                )
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (state.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = PlaySoptTheme.colors.gray2,
                        style = PlaySoptTheme.typography.m18,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                innerTextField()
            }

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                painter = painterResource(R.drawable.ic_search_24),
                contentDescription = null,
                tint = PlaySoptTheme.colors.gray3,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable(onClick = onSearch),
            )
        }
    }

    BasicTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        onKeyboardAction = { onSearch() },
        lineLimits = TextFieldLineLimits.SingleLine,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(PlaySoptTheme.colors.gray5),
        decorator = decorator,
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaySoptSearchTextFieldPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            PlaySoptSearchTextField(
                state = rememberTextFieldState(),
                onSearch = {},
            )
            PlaySoptSearchTextField(
                state = rememberTextFieldState("솝트"),
                onSearch = {},
            )
        }
    }
}
