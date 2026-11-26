package org.sopt.play.data.repository

import kotlinx.coroutines.flow.first
import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.local.UserLocalDataSource
import org.sopt.play.data.remote.dto.request.CreatePostRequestDto
import org.sopt.play.data.remote.dto.request.UpdatePostRequestDto
import org.sopt.play.data.remote.requireData
import org.sopt.play.data.remote.service.PostService

class PostRepositoryImpl(
    private val postService: PostService,
    private val userLocalDataSource: UserLocalDataSource,
) : PostRepository {

    override suspend fun getPosts(keyword: String?) =
        suspendRunCatching {
            postService.getPosts(keyword = keyword).requireData().posts
        }

    override suspend fun createPost(title: String, content: String) =
        suspendRunCatching {
            val author = userLocalDataSource.registeredName.first()
                ?: error("저장된 이름이 없어요.")
            postService.createPost(CreatePostRequestDto(title, content, author))
            Unit
        }

    override suspend fun updatePost(postId: Long, title: String, content: String) =
        suspendRunCatching {
            postService.updatePost(postId, UpdatePostRequestDto(title, content))
            Unit
        }

    override suspend fun deletePost(postId: Long) =
        suspendRunCatching {
            postService.deletePost(postId)
            Unit
        }
}
