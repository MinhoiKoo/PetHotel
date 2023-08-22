package com.minhoi.pethotel.data

import com.minhoi.pethotel.data.*
import com.minhoi.pethotel.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.lang.ref.ReferenceQueue

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

    @Multipart
    @POST("api/hotel")
    suspend fun registerHotel(
        @Query("name") name : String,
        @Query("bsNum") bsNum : String,
        @Query("description") description : String,
        @Part imageFiles : List<MultipartBody.Part>
    ) : Response<CreateOwnerResponse>

    @GET("{imageName}")
    suspend fun getImage(@Path("imageName") imageName : String) : Response<String>

    @GET("{imageNames}")
    suspend fun getImages(@Path("imageNames") imageNames : String) : Response<List<String>>
}
