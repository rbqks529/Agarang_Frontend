package com.example.myapplication.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiaryIF {
    @GET("/api/memory")
    fun getDiaryMonth(@Query("viewType")
                      viewType: String
    ) : Call<BaseData<PostResult>>

}