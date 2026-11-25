package org.sopt.play.data.remote.service

import org.sopt.play.data.remote.BaseResponse
import org.sopt.play.data.remote.dto.request.AuthorizationCodeRequestDto
import org.sopt.play.data.remote.dto.response.AuthorizationCodeResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService {
    @POST("api/v1/members/authorization-code")
    suspend fun issueAuthorizationCode(
        @Body request: AuthorizationCodeRequestDto,
    ): BaseResponse<AuthorizationCodeResponseDto>
}
