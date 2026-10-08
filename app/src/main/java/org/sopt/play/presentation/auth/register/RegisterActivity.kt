package org.sopt.play.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.AuthValidator

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaySoptTheme {
                val nameState = rememberTextFieldState()
                val emailState = rememberTextFieldState()
                val passwordState = rememberTextFieldState()
                val passwordConfirmState = rememberTextFieldState()

                val email = emailState.text.toString()
                val password = passwordState.text.toString()
                val passwordConfirm = passwordConfirmState.text.toString()

                val registerEnabled = AuthValidator.isValidEmail(email) &&
                    AuthValidator.isValidPassword(password) &&
                    AuthValidator.isValidPasswordConfirm(password, passwordConfirm)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterScreen(
                        nameState = nameState,
                        emailState = emailState,
                        passwordState = passwordState,
                        passwordConfirmState = passwordConfirmState,
                        registerEnabled = registerEnabled,
                        onRegisterClick = { register(email = email, password = password) },
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    )
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_EMAIL, email)
            putExtra(EXTRA_PASSWORD, password)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASSWORD = "extra_password"
    }
}
