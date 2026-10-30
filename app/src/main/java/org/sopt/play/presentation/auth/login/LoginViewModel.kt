package org.sopt.play.presentation.auth.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.play.data.local.UserLocalDataSource

class LoginViewModel(
    private val userLocalDataSource: UserLocalDataSource
) : ViewModel() {
    val emailState = TextFieldState()
    val passwordState = TextFieldState()

    val uiState: StateFlow<LoginUiState> = combine(
        snapshotFlow { emailState.text.toString() },
        snapshotFlow { passwordState.text.toString() },
    ) { email, password ->
        LoginUiState(email, password)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LoginUiState())

    private val _sideEffect = Channel<LoginSideEffect>()
    val sideEffect: Flow<LoginSideEffect> = _sideEffect.receiveAsFlow()

    private var registeredEmail: String? = null
    private var registeredPassword: String? = null

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.RegisteredUserReceived -> {
                registeredEmail = intent.email
                registeredPassword = intent.password
            }

            LoginIntent.LoginClick -> login()
            LoginIntent.RegisterClick -> viewModelScope.launch {
                _sideEffect.send(LoginSideEffect.NavigateToRegister)
            }
        }
    }

    private fun login() {
        val state = uiState.value
        if (!state.loginEnabled) return

        viewModelScope.launch {
            val registeredEmail = userLocalDataSource.registeredEmail.first()
            val registeredPassword = userLocalDataSource.registeredPassword.first()

            val isCredentialMatched = registeredEmail != null &&
                    state.email == registeredEmail &&
                    state.password == registeredPassword

            if (isCredentialMatched) {
                userLocalDataSource.setAutoLogin(true)
                _sideEffect.send(LoginSideEffect.NavigateToHome)
            } else {
                _sideEffect.send(LoginSideEffect.ShowToast("이메일 또는 비밀번호가 올바르지 않아요."))
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                LoginViewModel(UserLocalDataSource(this[APPLICATION_KEY]!!))
            }
        }
    }
}
