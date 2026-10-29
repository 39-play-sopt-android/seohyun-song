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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
@Composable
fun RegisterRoute(
    onRegisterSuccess: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifeCycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.event, lifeCycleOwner) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                when (event) {
                    is RegisterEvent.RegisterSuccess ->
                        onRegisterSuccess(event.email, event.password)
                }
            }
        }
    }

    RegisterScreen(
        nameState = viewModel.nameState,
        emailState = viewModel.emailState,
        passwordState = viewModel.passwordState,
        passwordConfirmState = viewModel.passwordConfirmState,
        uiState = uiState,
        onRegisterClick = viewModel::register,
        modifier = modifier,
    )
}

@Composable
fun RegisterScreen(
    nameState: TextFieldState,
    emailState: TextFieldState,
    passwordState: TextFieldState,
    passwordConfirmState: TextFieldState,
    uiState: RegisterUiState,
    onRegisterClick: () -> Unit,
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
            .imePadding()
            .verticalScroll(rememberScrollState())
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            onKeyboardAction = moveFocusDown,
        )

        Spacer(modifier = Modifier.height(32.dp))

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
                imeAction = ImeAction.Next,
            ),
            onKeyboardAction = moveFocusDown,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlaySoptTextField(
            label = "비밀번호 확인",
            state = passwordConfirmState,
            placeholder = "6자 이상의 비밀번호",
            errorMsg = uiState.passwordConfirmErrorMsg,
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            onKeyboardAction = clearFocus,
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlaySoptButton(
            text = "회원가입",
            enabled = uiState.registerEnabled,
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@FontScalePreviews
@Composable
private fun RegisterPreview() {
    PlaySoptTheme {
        RegisterScreen(
            nameState = rememberTextFieldState(),
            emailState = rememberTextFieldState(),
            passwordState = rememberTextFieldState(),
            passwordConfirmState = rememberTextFieldState(),
            uiState = RegisterUiState(),
            onRegisterClick = {},
        )
    }
}
