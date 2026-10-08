package org.sopt.play.presentation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.button.PlaySoptButton
import org.sopt.play.core.designsystem.component.textfield.PlaySoptTextField
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable

@Composable
fun LoginScreen(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    loginEnabled: Boolean,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white)
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "이메일로 로그인하기",
            style = PlaySoptTheme.typography.b28,
            color = PlaySoptTheme.colors.black,
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlaySoptTextField(
            label = "이메일 주소",
            state = emailState,
            placeholder = "abc@email.com",
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlaySoptTextField(
            label = "비밀번호",
            state = passwordState,
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlaySoptButton(
            text = "로그인",
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "아직 계정이 없으신가요?",
                style = PlaySoptTheme.typography.m14,
                color = PlaySoptTheme.colors.gray3,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "회원가입하기",
                style = PlaySoptTheme.typography.m14,
                color = PlaySoptTheme.colors.gray6,
                modifier = Modifier.noRippleClickable(
                    enabled = true,
                    onClick = onRegisterClick,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    PlaySoptTheme {
        LoginScreen(
            emailState = rememberTextFieldState(),
            passwordState = rememberTextFieldState(),
            loginEnabled = true,
            onLoginClick = {},
            onRegisterClick = {},
        )
    }
}