package org.sopt.play.data.remote

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.play.BuildConfig
import org.sopt.play.data.local.UserLocalDataSource
import org.sopt.play.data.remote.service.MemberService
import org.sopt.play.data.remote.service.PostService
import retrofit2.Retrofit

object ApiFactory {
    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        if (::retrofit.isInitialized) return

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(UserLocalDataSource(context)))
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val postService: PostService by lazy { retrofit.create(PostService::class.java) }
    val memberService: MemberService by lazy { retrofit.create(MemberService::class.java) }
}
