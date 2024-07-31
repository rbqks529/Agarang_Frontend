package com.example.myapplication.Data.Response

/*data class Memory(
    val writer: String,
    val content: String,
    val hashTags: List<String>,
    val date: String,
    val favorite: Boolean
)*/

data class Result(
    val thumbNails: List<String>,
    val memories: List<Memory>
)

data class DiaryCardResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Result
)


data class DiaryMonthResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: MonthResult
)

data class MonthResult(
    val monthlyMemories: List<MonthlyMemory>
)

data class MonthlyMemory(
    val date: String,
    val imageUrl: String
)










