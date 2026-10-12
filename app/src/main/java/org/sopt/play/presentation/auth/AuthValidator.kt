package org.sopt.play.presentation.auth

import android.util.Patterns

object AuthValidator {
    private const val MIN_PASSWORD_LENGTH = 6

    const val EMAIL_ERROR_MSG = "올바른 이메일을 입력해주세요."
    const val PASSWORD_ERROR_MSG = "비밀번호는 6자 이상 입력해주세요."
    const val PASSWORD_CONFIRM_ERROR_MSG = "비밀번호와 동일하게 입력해주세요."

    fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH

    fun isValidPasswordConfirm(password: String, passwordConfirm: String): Boolean =
        password == passwordConfirm

    fun emailErrorMsg(email: String): String? =
        if (email.isNotEmpty() && !isValidEmail(email)) EMAIL_ERROR_MSG else null

    fun passwordErrorMsg(password: String): String? =
        if (password.isNotEmpty() && !isValidPassword(password)) PASSWORD_ERROR_MSG else null

    fun passwordConfirmErrorMsg(password: String, passwordConfirm: String): String? =
        if (passwordConfirm.isNotEmpty() && !isValidPasswordConfirm(password, passwordConfirm)) {
            PASSWORD_CONFIRM_ERROR_MSG
        } else {
            null
        }
}
