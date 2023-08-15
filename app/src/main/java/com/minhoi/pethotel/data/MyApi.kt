package com.minhoi.pethotel.data

import com.minhoi.pethotel.data.*
import com.minhoi.pethotel.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface MyApi {

    data class TokenResponse(val token : String, val name : String, val error : String)

    // 전체 멤버 수, 리스트 조회
    @GET("api/members")
    suspend fun getMemberList() : Response<List<MemberDto>>

    @POST("api/members")
    suspend fun createMember(@Body member : CreateMemberRequest) : Response<CreateMemberResponse>

    @PUT("api/members/{id}")
    suspend fun updateMember(@Path("id") id : Long, @Body request : UpdateMemberRequest) : Response<UpdateMemberResponse>

    // Login 요청하면 Server에서 Token 받음
    @POST("api/members/login")
    suspend fun login(@Body member : MemberDto) : Response<TokenResponse>


}