package com.minhoi.pethotel.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    val BASE_URL = "http://13.124.178.209:8080/"

    // 로그인시 Token 받아오는 객체
    private val clientWithOutToken = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstanceWithoutToken() : Retrofit {
        return clientWithOutToken
    }

    fun createService(jwtToken: String): Retrofit {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(jwtToken)) // JwtInterceptor가 있는 OkHttp 클라이언트 사용
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client
    }

    fun createOkHttpClient(jwtToken: String): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(JwtInterceptor(jwtToken)) // JwtInterceptor 추가
            .build()
    }

    class JwtInterceptor(private val jwtToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $jwtToken")
                .build()

            return chain.proceed(modifiedRequest)
        }
    }

}