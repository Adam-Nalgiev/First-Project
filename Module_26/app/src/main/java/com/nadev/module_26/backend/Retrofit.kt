package com.nadev.module_26.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("json")
    suspend fun getSunsetTime(
        @Query("lat") lat:Float,
        @Query("lng") lng:Float,
        @Query("date") date:String,
        @Query("formatted") formatted:Int = 1
    ):SunsetResponse
}

val retrofit: API = Retrofit.Builder()
    .baseUrl("https://api.sunrise-sunset.org/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(API::class.java)