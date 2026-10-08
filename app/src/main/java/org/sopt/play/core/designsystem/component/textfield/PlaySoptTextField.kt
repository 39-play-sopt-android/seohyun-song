package org.sopt.play.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PlaySoptTextField(
    label: String,
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    textColor: Color = PlaySoptTheme.colors.gray5,
    errorMsg: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
) {
    val colors = PlaySoptTheme.colors

    val internalInteractionSource = remember { MutableInteractionSource() }
    val isFocused by internalInteractionSource.collectIsFocusedAsState()

    val isError = errorMsg != null
    val isEmpty = state.text.isEmpty()

    val borderColor = when {
        isError -> colors.red
        isFocused -> colors.gray5
        else -> colors.gray2
    }
    val currentTextColor = if (isError) colors.gray6 else textColor
    val textStyle = PlaySoptTheme.typography.m18.copy(color = currentTextColor)
    val cursorBrush = SolidColor(currentTextColor)

    val decorator = TextFieldDecorator { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(all = 16.dp),
        ) {
            if (isEmpty) {
                Text(
                    text = placeholder,
                    color = colors.gray2,
                    style = PlaySoptTheme.typography.m18,
                )
            }
            innerTextField()
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            color = colors.gray6,
            style = PlaySoptTheme.typography.sb16,
            modifier = Modifier.padding(start = 8.dp),
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (isPassword) {
            BasicSecureTextField(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                onKeyboardAction = onKeyboardAction,
                interactionSource = internalInteractionSource,
                cursorBrush = cursorBrush,
                decorator = decorator,
            )
        } else {
            BasicTextField(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                onKeyboardAction = onKeyboardAction,
                lineLimits = lineLimits,
                interactionSource = internalInteractionSource,
                cursorBrush = cursorBrush,
                decorator = decorator,
            )
        }

        if (errorMsg != null) {
            Text(
                text = errorMsg,
                color = colors.red,
                style = PlaySoptTheme.typography.m14,
                modifier = Modifier.padding(start = 8.dp, top = 6.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaySoptTextFieldPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            PlaySoptTextField(
                label = "이메일 주소",
                state = rememberTextFieldState(),
                placeholder = "abc@email.com",
            )
            PlaySoptTextField(
                label = "이메일 주소",
                state = rememberTextFieldState("abc@email.com"),
                placeholder = "abc@email.com",
            )
            PlaySoptTextField(
                label = "이메일 주소",
                state = rememberTextFieldState("abc.com"),
                placeholder = "abc@email.com",
                errorMsg = "올바른 이메일을 입력해주세요.",
            )
            PlaySoptTextField(
                label = "비밀번호",
                state = rememberTextFieldState("password"),
                placeholder = "비밀번호를 입력해주세요",
                isPassword = true,
            )
        }
    }
}
