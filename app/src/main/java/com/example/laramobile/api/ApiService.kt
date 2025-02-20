package com.example.laramobile.api

import com.example.laramobile.api.model.Frase
import com.example.laramobile.api.model.LoginResponse
import com.example.laramobile.api.model.Sylabus
import com.example.laramobile.api.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("usuarios/")
    suspend fun getUsers(): List<User>

    @FormUrlEncoded
    @POST("login/token")
    suspend fun logUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("frases/")
    suspend fun getFrases(): List<Frase>

    @GET("sylabus")
    suspend fun getSylabus(): List<Sylabus>
}