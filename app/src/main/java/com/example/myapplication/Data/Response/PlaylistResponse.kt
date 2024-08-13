package com.example.myapplication.Data.Response

data class AllPlaylistResponse(
    val code:Int,
    val status:Int,
    val message:String,
    val result:AllPlaylistResult
)

data class AllPlaylistResult(
    val playlists:List<Playlist>
)

data class Playlist(
    val id:Int,
    val name: String,
    val imageUrl:String
)