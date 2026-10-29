package org.sopt.play.presentation.auth.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val emailState = TextFieldState()
    val passwordState = TextFieldState()

    val uiState: StateFlow<LoginUiState> = combine(
        snapshotFlow { emailState.text.toString() },
        snapshotFlow { passwordState.text.toString() },
    ) { email, password ->
        LoginUiState(email, password)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LoginUiState())

    private val _event = Channel<LoginEvent>()
    val event: Flow<LoginEvent> = _event.receiveAsFlow()

    fun login(registeredEmail: String?, registeredPassword: String?) {
        val state = uiState.value
        if (!state.loginEnabled) return

        val isCredentialMatched = registeredEmail != null &&
                state.email == registeredEmail &&
                state.password == registeredPassword

        viewModelScope.launch {
            _event.send(
                if (isCredentialMatched) LoginEvent.LoginSuccess
                else LoginEvent.ShowToast("이메일 또는 비밀번호가 올바르지 않아요.")
            )
        }
    }
}
