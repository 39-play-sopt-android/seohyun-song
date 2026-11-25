package org.sopt.play.data.remote.service

import org.sopt.play.data.remote.BaseResponse
import org.sopt.play.data.remote.dto.request.CreatePostRequestDto
import org.sopt.play.data.remote.dto.request.UpdatePostRequestDto
import org.sopt.play.data.remote.dto.response.PostResponseDto
import org.sopt.play.data.remote.dto.response.PostSliceResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("keyword") keyword: String? = null,
        @Query("size") size: Int = 30,
    ): BaseResponse<PostSliceResponseDto>

    @POST("api/v1/posts")
    suspend fun createPost(
        @Body request: CreatePostRequestDto,
    ): BaseResponse<PostResponseDto>

    @PUT("api/v1/posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Long,
        @Body request: UpdatePostRequestDto,
    ): BaseResponse<PostResponseDto>

    @DELETE("api/v1/posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>
}
