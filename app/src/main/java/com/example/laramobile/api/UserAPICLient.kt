package com.example.laramobile.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
//  private const val BASE_URL = "http://10.0.2.2:8000/"  // Localhost
//    private const val BASE_URL = "https://larabackend.onrender.com/"
private const val BASE_URL = "https://0p1x36b4-8000.uks1.devtunnels.ms/"


    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}