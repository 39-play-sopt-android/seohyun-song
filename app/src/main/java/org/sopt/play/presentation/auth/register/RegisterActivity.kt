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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.AuthExtraKeys
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

                val registerEnabled by remember {
                    derivedStateOf {
                        val password = passwordState.text.toString()

                        nameState.text.isNotBlank() &&
                                AuthValidator.isValidEmail(emailState.text.toString()) &&
                                AuthValidator.isValidPassword(password) &&
                                AuthValidator.isValidPasswordConfirm(
                                    password,
                                    passwordConfirmState.text.toString()
                                )
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterScreen(
                        nameState = nameState,
                        emailState = emailState,
                        passwordState = passwordState,
                        passwordConfirmState = passwordConfirmState,
                        registerEnabled = registerEnabled,
                        onRegisterClick = {
                            register(
                                email = emailState.text.toString(),
                                password = passwordState.text.toString(),
                            )
                        },
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
            putExtra(AuthExtraKeys.EMAIL, email)
            putExtra(AuthExtraKeys.PASSWORD, password)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
