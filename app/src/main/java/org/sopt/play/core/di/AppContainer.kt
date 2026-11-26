package org.sopt.play.core.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.play.BuildConfig
import org.sopt.play.data.local.UserLocalDataSource
import org.sopt.play.data.remote.AuthInterceptor
import org.sopt.play.data.remote.service.MemberService
import org.sopt.play.data.remote.service.PostService
import org.sopt.play.data.repository.AuthRepository
import org.sopt.play.data.repository.AuthRepositoryImpl
import org.sopt.play.data.repository.PostRepository
import org.sopt.play.data.repository.PostRepositoryImpl
import retrofit2.Retrofit
import retrofit2.create

class AppContainer(context: Context) {
    private val appContext = context.applicationContext

    private val userLocalDataSource by lazy {
        UserLocalDataSource(appContext)
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userLocalDataSource))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                }
            ).build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val memberService by lazy { retrofit.create<MemberService>() }
    private val postService by lazy { retrofit.create<PostService>() }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(userLocalDataSource, memberService)
    }
    val postRepository: PostRepository by lazy {
        PostRepositoryImpl(postService, userLocalDataSource)
    }
}
