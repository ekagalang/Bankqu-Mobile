package com.bankqu.mobile.data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val emailVerifiedAt: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)