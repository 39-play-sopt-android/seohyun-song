package org.sopt.play.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isAutoLogin: Flow<Boolean>
    val registeredName: Flow<String?>

    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun isCredentialMatched(email: String, password: String): Boolean
    suspend fun ensureAuthorizationCode(): Result<Unit>
    suspend fun setAutoLogin(enabled: Boolean): Result<Unit>
}
