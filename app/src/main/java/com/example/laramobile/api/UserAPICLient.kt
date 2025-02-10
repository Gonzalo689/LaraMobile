package com.example.laramobile.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
//    private const val BASE_URL = "http://10.0.2.2:8000/"  // Localhost
    private const val BASE_URL = "https://larabackend.onrender.com/"

    // Crear la instancia de Retrofit
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Usamos Gson para convertir JSON a objetos
            .build()
    }

    // Crear una instancia del servicio API
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}