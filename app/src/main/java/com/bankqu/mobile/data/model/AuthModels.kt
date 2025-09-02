package com.bankqu.mobile.data.model

// Request Models
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

// Response Models
data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData? = null
)

data class AuthData(
    val user: User,
    val access_token: String
)

data class BaseResponse(
    val success: Boolean,
    val message: String
)