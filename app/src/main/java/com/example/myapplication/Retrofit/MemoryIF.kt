package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Request.FirstAnsRequest
import com.example.myapplication.Data.Response.FirstAnsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemoryIF {
    @POST("/api/memory/ai/first-ans")
    fun sendFirstAns(@Body result:FirstAnsRequest): Call<FirstAnsResponse>
}