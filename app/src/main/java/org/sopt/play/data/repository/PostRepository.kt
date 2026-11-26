package org.sopt.play.data.repository

import org.sopt.play.data.remote.dto.response.PostSliceResponseDto

interface PostRepository {
    suspend fun getPosts(keyword: String?, cursor: Long?): Result<PostSliceResponseDto>
    suspend fun createPost(title: String, content: String): Result<Unit>
    suspend fun updatePost(postId: Long, title: String, content: String): Result<Unit>
    suspend fun deletePost(postId: Long): Result<Unit>
}
