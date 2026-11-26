package org.sopt.play.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.sopt.play.core.di.appContainer
import org.sopt.play.core.navigation.Route
import org.sopt.play.data.repository.AuthRepository
import org.sopt.play.presentation.auth.login.navigation.Login
import org.sopt.play.presentation.home.navigation.Home

class MainViewModel(
    authRepository: AuthRepository,
) : ViewModel() {
    val startDestination: StateFlow<Route?> = flow {
        emit(
            if (authRepository.isAutoLogin.first()) Home else Login
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                MainViewModel(appContainer.authRepository)
            }
        }
    }
}
