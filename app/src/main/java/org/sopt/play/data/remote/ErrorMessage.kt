package org.sopt.play.data.remote

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import retrofit2.HttpException

private val errorJson = Json { ignoreUnknownKeys = true }

fun Throwable.serverMessage(): String? {
    val body = (this as? HttpException)?.response()?.errorBody()?.string() ?: return null

    return runCatching {
        errorJson.decodeFromString<BaseResponse<JsonElement>>(body).message
    }.getOrNull()?.takeIf { it.isNotBlank() }
}
