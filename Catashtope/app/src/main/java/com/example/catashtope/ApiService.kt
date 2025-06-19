// file: ApiService.kt
package com.example.catashtope // Ganti dengan package name Anda

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Base URL dari API
private const val BASE_URL = "https://api.open-meteo.com/"

// Membuat singleton instance dari Retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Interface untuk mendefinisikan endpoint API
interface ApiService {
    @GET("v1/forecast?latitude=-7.2492&longitude=112.7508&current=snowfall,is_day,temperature_2m,rain")
    suspend fun getWeatherData(): Response<WeatherResponse>
}

// Objek untuk mengakses service dari mana saja di aplikasi
object WeatherApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}