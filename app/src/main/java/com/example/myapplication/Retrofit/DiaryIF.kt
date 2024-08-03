package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Request.Memory2Request
import com.example.myapplication.Data.Response.DiaryMonthResponse
import com.example.myapplication.Data.Response.FavoriteResponse
import com.example.myapplication.Data.Response.Memory2Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DiaryIF {
    @GET("/api/memory")
    fun getFavorite(
        @Query("viewType") viewType: String
    ):Call<FavoriteResponse>

    @GET("api/memory")
    fun getMonthlyMemories(@Query("viewType") viewType: String, @Query("date") date:String): Call<DiaryMonthResponse>

    @POST("/api/memory/ai/second-ans")
    fun sendMemoryDetails(@Body request: Memory2Request): Call<Memory2Response>
}