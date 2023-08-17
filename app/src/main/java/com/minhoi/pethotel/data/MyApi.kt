package com.minhoi.pethotel.data

import com.minhoi.pethotel.data.*
import com.minhoi.pethotel.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface MyApi {



    // 자기 자신 조회
    @GET("api/members/myinfo")
    suspend fun getMemberList() : Response<MyInfoResponse>

    @POST("api/members")
    suspend fun createMember(@Body member : CreateMemberRequest) : Response<CreateMemberResponse>

    @PUT("api/members/{id}")
    suspend fun updateMember(@Path("id") id : Long, @Body request : UpdateMemberRequest) : Response<UpdateMemberResponse>

    // Login 요청하면 Server에서 Token 받음
    @POST("api/members/login")
    suspend fun userLogin(@Body member : MemberDto) : Response<LoginResponse>

    @POST("api/members/logout")
    suspend fun logout() : Response<LogoutResponse>

    @POST("api/owners/login")
    suspend fun ownerLogin(@Body member : MemberDto) : Response<LoginResponse>

    @GET("api/owners/myinfo")
    suspend fun ownerInfo() : Response<MyInfoResponse>

    @POST("api/owners")
    suspend fun createOwner(@Body owner : CreateOwnerRequest) : Response<CreateOwnerResponse>

    @POST("api/hotel")
    suspend fun createHotel(@Body hotel : CreateHotelRequest) : Response<CreateOwnerResponse>

    @GET("api/hotels")
    suspend fun getHotelList() : Response<List<HotelDto>>

    @GET("api/hotels/hotelinfo/{id}")
    suspend fun getHotelInfo(@Path("id") id : Long) : Response<HotelInfoDto>

}
