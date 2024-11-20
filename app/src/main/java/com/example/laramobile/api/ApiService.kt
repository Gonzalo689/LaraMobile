package com.example.laramobile.api

import com.example.laramobile.api.model.Frase
import com.example.laramobile.api.model.Usuario
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getUsers(): List<Usuario>
    @GET("frases/")
    suspend fun getFrases(): List<Frase>
}