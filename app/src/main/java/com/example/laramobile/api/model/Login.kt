package com.example.laramobile.api.model

data class LoginRequest(val mail: String, val password: String)

data class LoginResponse(
    val success: Boolean,
    val token: String? = null,
    val message: String
)