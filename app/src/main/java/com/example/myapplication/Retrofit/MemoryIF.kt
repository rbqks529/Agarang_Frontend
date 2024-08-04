package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Request.FirstAnsRequest
import com.example.myapplication.Data.Response.FirstAnsResponse
import com.example.myapplication.Data.Response.MemoryImageToQuestionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MemoryIF {
    @POST("/api/memory/ai/first-ans")
    fun sendFirstAns(@Body result:FirstAnsRequest): Call<FirstAnsResponse>

    @Multipart
    @POST("/api/memory/ai/image-to-question")
    fun sendImageToQuestion(@Part image: MultipartBody.Part): Call<MemoryImageToQuestionResponse>
}