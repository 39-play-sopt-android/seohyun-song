package org.sopt.play.presentation.auth.login

import org.sopt.play.presentation.auth.AuthValidator

data class LoginUiState(
    val email: String = "",
    val password: String = "",
) {
    val emailErrorMsg: String?
        get() = AuthValidator.emailErrorMsg(email)

    val passwordErrorMsg: String?
        get() = AuthValidator.passwordErrorMsg(password)

    val loginEnabled: Boolean
        get() = AuthValidator.isValidEmail(email) &&
                AuthValidator.isValidPassword(password)
}

sealed interface LoginIntent {
    data class RegisteredUserReceived(val email: String?, val password: String?) : LoginIntent
    data object LoginClick : LoginIntent
    data object RegisterClick: LoginIntent
}

sealed interface LoginSideEffect {
    data object NavigateToHome : LoginSideEffect
    data object NavigateToRegister : LoginSideEffect
    data class ShowToast(val message: String) : LoginSideEffect
}
