package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Response.HomeChangeCharResponse
import com.example.myapplication.Data.Response.HomeChildResponse
import com.example.myapplication.Data.Response.HomeResponse
import com.example.myapplication.Data.Response.HomeSettingResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeIF {
    //홈 메인
    @GET("/api/home")
    fun getHomeData(): Call<HomeResponse>

    //홈 설정
    @GET("api/home/setting")
    fun getSettingData(): Call<HomeSettingResponse>

    //아이 정보 조회
    @GET("/api/home/setting/baby")
    fun getChildInfoData(): Call<HomeChildResponse>

    //홈 캐릭터 조회
    @GET("api/home/setting/character")
    fun getCharacterData(): Call<HomeChangeCharResponse>
}