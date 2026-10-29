package org.sopt.play.presentation.auth.register

import org.sopt.play.presentation.auth.AuthValidator

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
) {
    val emailErrorMsg: String?
        get() = AuthValidator.emailErrorMsg(email)

    val passwordErrorMsg: String?
        get() = AuthValidator.passwordErrorMsg(password)

    val passwordConfirmErrorMsg: String?
        get() = AuthValidator.passwordConfirmErrorMsg(password, passwordConfirm)

    val registerEnabled: Boolean
        get() = name.isNotBlank() &&
                AuthValidator.isValidEmail(email) &&
                AuthValidator.isValidPassword(password) &&
                AuthValidator.isValidPasswordConfirm(password, passwordConfirm)
}

sealed interface RegisterEvent {
    data class RegisterSuccess(val email: String, val password: String) : RegisterEvent
}
