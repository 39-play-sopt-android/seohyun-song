package org.sopt.play.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.local.UserLocalDataSource
import org.sopt.play.data.remote.dto.request.AuthorizationCodeRequestDto
import org.sopt.play.data.remote.requireData
import org.sopt.play.data.remote.service.MemberService

class AuthRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val memberService: MemberService,
) : AuthRepository {

    override val isAutoLogin: Flow<Boolean> = userLocalDataSource.isAutoLogin

    override suspend fun register(name: String, email: String, password: String) =
        userLocalDataSource.saveUser(name, email, password)

    override suspend fun isCredentialMatched(email: String, password: String): Boolean {
        val registeredEmail = userLocalDataSource.registeredEmail.first()
        val registeredPassword = userLocalDataSource.registeredPassword.first()

        return registeredEmail != null &&
                email == registeredEmail &&
                password == registeredPassword
    }

    override suspend fun ensureAuthorizationCode(): Result<Unit> =
        suspendRunCatching {
            if (userLocalDataSource.authorizationCode.first() != null) return@suspendRunCatching

            val code = memberService
                .issueAuthorizationCode(AuthorizationCodeRequestDto(part = PART))
                .requireData()
                .authorizationCode

            userLocalDataSource.saveAuthorizationCode(code).getOrThrow()
        }

    override suspend fun setAutoLogin(enabled: Boolean): Result<Unit> =
        userLocalDataSource.setAutoLogin(enabled)

    private companion object {
        const val PART = "ANDROID"
    }
}
