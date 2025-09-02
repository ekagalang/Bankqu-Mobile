package com.bankqu.mobile.data.api

import com.bankqu.mobile.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface `ApiService.kt` {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("logout")
    suspend fun logout(): Response<BaseResponse>

    @GET("me")
    suspend fun getProfile(): Response<AuthResponse>

    @GET("health")
    suspend fun healthCheck(): Response<BaseResponse>
}