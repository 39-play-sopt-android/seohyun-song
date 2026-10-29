package org.sopt.play.presentation.auth.register

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

class RegisterViewModel : ViewModel() {
    val nameState = TextFieldState()
    val emailState = TextFieldState()
    val passwordState = TextFieldState()
    val passwordConfirmState = TextFieldState()

    val uiState: StateFlow<RegisterUiState> = combine(
        snapshotFlow { nameState.text.toString() },
        snapshotFlow { emailState.text.toString() },
        snapshotFlow { passwordState.text.toString() },
        snapshotFlow { passwordConfirmState.text.toString() },
    ) { name, email, password, passwordConfirm ->
        RegisterUiState(name, email, password, passwordConfirm)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), RegisterUiState())

    private val _event = Channel<RegisterEvent>()
    val event: Flow<RegisterEvent> = _event.receiveAsFlow()

    fun register() {
        val state = uiState.value
        if (!state.registerEnabled) return

        viewModelScope.launch {
            _event.send(RegisterEvent.RegisterSuccess(state.email, state.password))
        }
    }
}
