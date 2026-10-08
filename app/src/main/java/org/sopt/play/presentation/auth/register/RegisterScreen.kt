package org.sopt.play.presentation.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
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
import org.sopt.play.presentation.auth.AuthValidator

@Composable
fun RegisterScreen(
    nameState: TextFieldState,
    emailState: TextFieldState,
    passwordState: TextFieldState,
    passwordConfirmState: TextFieldState,
    registerEnabled: Boolean,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "이메일로 회원가입",
            style = PlaySoptTheme.typography.b28,
            color = PlaySoptTheme.colors.black,
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlaySoptTextField(
            label = "이름",
            state = nameState,
            placeholder = "홍길동",
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlaySoptTextField(
            label = "이메일 주소",
            state = emailState,
            placeholder = "abc@email.com",
            errorMsg = AuthValidator.emailErrorMsg(emailState.text.toString()),
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlaySoptTextField(
            label = "비밀번호",
            state = passwordState,
            placeholder = "6자 이상의 비밀번호",
            errorMsg = AuthValidator.passwordErrorMsg(passwordState.text.toString()),
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlaySoptTextField(
            label = "비밀번호 확인",
            state = passwordConfirmState,
            placeholder = "6자 이상의 비밀번호",
            errorMsg = AuthValidator.passwordConfirmErrorMsg(
                password = passwordState.text.toString(),
                passwordConfirm = passwordConfirmState.text.toString(),
            ),
            isPassword = true,
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlaySoptButton(
            text = "회원가입",
            enabled = registerEnabled,
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun registerPreview() {
    PlaySoptTheme {
        RegisterScreen(
            nameState = rememberTextFieldState(),
            emailState = rememberTextFieldState(),
            passwordState = rememberTextFieldState(),
            passwordConfirmState = rememberTextFieldState(),
            registerEnabled = false,
            onRegisterClick = {},
        )
    }
}