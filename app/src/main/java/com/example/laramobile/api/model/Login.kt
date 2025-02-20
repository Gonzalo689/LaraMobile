package com.example.laramobile.api.model


data class LoginResponse(
    val token: String? = null,
    val message: String,
    val user: User? = null
)