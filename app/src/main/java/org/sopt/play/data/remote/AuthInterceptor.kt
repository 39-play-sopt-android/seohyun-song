package org.sopt.play.data.remote

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.sopt.play.data.local.UserLocalDataSource

class AuthInterceptor(
    private val userLocalDataSource: UserLocalDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val code = runBlocking {
            userLocalDataSource.authorizationCode.first()
        }

        val request = chain.request().newBuilder().apply {
            if (!code.isNullOrBlank()) header(AUTHORIZATION, code)
        }.build()

        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
