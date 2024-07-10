package com.example.myapplication.Diary

import java.io.Serializable


data class DiaryMainDayData(
    val imageResId: Int,
    val date: String,
    val bookmark: Int = 0
) : Serializable {
    val year: Int
    val month: Int
    val day: Int

    init {
        val parts = date.split("/").map { it.trim().toInt() }
        year = parts[0]
        month = parts[1]
        day = parts[2]
    }
}