package com.example.myapplication.Data.Request

data class FirstAnsRequest(
    val id:String,
    val text: String
)

data class bookmarkSetRequest(
    val memoryId: String
)