package org.sopt.play.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.sopt.play.core.navigation.Route
import org.sopt.play.data.local.UserLocalDataSource
import org.sopt.play.presentation.auth.login.navigation.Login

class MainViewModel(
    userLocalDataSource: UserLocalDataSource,
) : ViewModel() {
    val startDestination: StateFlow<Route?> = flow {
        emit(
            if (userLocalDataSource.isAutoLogin.first()) Home else Login
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                MainViewModel(UserLocalDataSource(this[APPLICATION_KEY]!!))
            }
        }
    }
}
