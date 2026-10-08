package org.sopt.play.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.AuthValidator
import org.sopt.play.presentation.auth.register.RegisterActivity
import org.sopt.play.presentation.main.MainActivity

class LoginActivity : ComponentActivity() {
    private var registeredEmail: String? = null
    private var registeredPassword: String? = null

    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            registeredEmail = result.data?.getStringExtra(RegisterActivity.EXTRA_EMAIL)
            registeredPassword = result.data?.getStringExtra(RegisterActivity.EXTRA_PASSWORD)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            registeredEmail = it.getString(RegisterActivity.EXTRA_EMAIL)
            registeredPassword = it.getString(RegisterActivity.EXTRA_PASSWORD)
        }
        enableEdgeToEdge()
        setContent {
            PlaySoptTheme {
                val emailState = rememberTextFieldState()
                val passwordState = rememberTextFieldState()

                val loginEnabled = AuthValidator.isValidEmail(emailState.text.toString()) &&
                    AuthValidator.isValidPassword(passwordState.text.toString())

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        emailState = emailState,
                        passwordState = passwordState,
                        loginEnabled = loginEnabled,
                        onLoginClick = {
                            login(
                                email = emailState.text.toString(),
                                password = passwordState.text.toString(),
                            )
                        },
                        onRegisterClick = {
                            registerLauncher.launch(Intent(this, RegisterActivity::class.java))
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(RegisterActivity.EXTRA_EMAIL, registeredEmail)
        outState.putString(RegisterActivity.EXTRA_PASSWORD, registeredPassword)
    }

    private fun login(email: String, password: String) {
        val isRegisteredUser = registeredEmail != null &&
            email == registeredEmail &&
            password == registeredPassword

        if (isRegisteredUser) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "이메일 또는 비밀번호가 올바르지 않아요.", Toast.LENGTH_SHORT).show()
        }
    }
}
