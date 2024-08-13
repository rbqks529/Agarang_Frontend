package com.example.myapplication.Retrofit

import com.example.myapplication.Data.Response.AllPlaylistResponse
import retrofit2.Call
import retrofit2.http.GET

interface PlaylistIF {
    @GET("api/playlists")
    fun getAllPlaylist(): Call<AllPlaylistResponse>
}