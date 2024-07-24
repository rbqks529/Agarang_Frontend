package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Response.DiaryMonthResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiaryIF {
    @GET("api/memory")
    fun getMonthlyMemories(@Query("viewType") viewType: String): Call<DiaryMonthResponse>
}