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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.core.designsystem.component.button.PlaySoptButton
import org.sopt.play.core.designsystem.component.textfield.PlaySoptTextField
import org.sopt.play.core.designsystem.preview.FontScalePreviews
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable
import org.sopt.play.core.extensions.showToast

@Composable
fun LoginRoute(
    registeredEmail: String?,
    registeredPassword: String?,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(registeredEmail, registeredPassword) {
        viewModel.onIntent(LoginIntent.RegisteredUserReceived(registeredEmail, registeredPassword))
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    LoginSideEffect.NavigateToHome -> onLoginSuccess()
                    LoginSideEffect.NavigateToRegister -> onRegisterClick()
                    is LoginSideEffect.ShowToast -> context.showToast(sideEffect.message)
                }
            }
        }
    }

    LoginScreen(
        emailState = viewModel.emailState,
        passwordState = viewModel.passwordState,
        uiState = uiState,
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}

@Composable
fun LoginScreen(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    uiState: LoginUiState,
    onIntent: (LoginIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val moveFocusDown = KeyboardActionHandler { focusManager.moveFocus(FocusDirection.Down) }
    val clearFocus = KeyboardActionHandler {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

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
            errorMsg = uiState.emailErrorMsg,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            onKeyboardAction = moveFocusDown,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlaySoptTextField(
            label = "비밀번호",
            state = passwordState,
            placeholder = "6자 이상의 비밀번호",
            errorMsg = uiState.passwordErrorMsg,
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            onKeyboardAction = clearFocus,
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlaySoptButton(
            text = "로그인",
            enabled = uiState.loginEnabled,
            onClick = { onIntent(LoginIntent.LoginClick) },
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
                    onClick = { onIntent(LoginIntent.RegisterClick) },
                )
            )
        }
    }
}

@FontScalePreviews
@Composable
private fun LoginPreview() {
    PlaySoptTheme {
        LoginScreen(
            emailState = rememberTextFieldState(),
            passwordState = rememberTextFieldState(),
            uiState = LoginUiState(),
            onIntent = {},
        )
    }
}
