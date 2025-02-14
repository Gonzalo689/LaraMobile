package com.example.laramobile.api

import com.example.laramobile.api.model.Frase
import com.example.laramobile.api.model.LoginRequest
import com.example.laramobile.api.model.LoginResponse
import com.example.laramobile.api.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("usuarios/")
    suspend fun getUsers(): List<Usuario>
    @POST("login/token")
    suspend fun logUser(@Body request:LoginRequest): LoginResponse

    @GET("frases/")
    suspend fun getFrases(): List<Frase>
}