package org.sopt.play.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _event = Channel<RegisterEvent>()
    val event: Flow<RegisterEvent> = _event.receiveAsFlow()

    fun updateName(name: String) = _uiState.update { it.copy(name = name) }

    fun updateEmail(email: String) = _uiState.update { it.copy(email = email) }

    fun updatePassword(password: String) = _uiState.update { it.copy(password = password) }

    fun updatePasswordConfirm(passwordConfirm: String) = _uiState.update { it.copy(passwordConfirm = passwordConfirm) }

    fun register() {
        val state = _uiState.value

        if (!state.registerEnabled) return

        viewModelScope.launch {
            _event.send(RegisterEvent.RegisterSuccess(state.email, state.password))
        }
    }
}
